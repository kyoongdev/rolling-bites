package kyoongdev.rolling_bites.common.social.kakao;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import kyoongdev.rolling_bites.common.apiClient.ApiClient;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.common.social.SocialErrorCode;
import kyoongdev.rolling_bites.common.social.SocialLogin;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KakaoSocialLogin extends SocialLogin {


  @Value("${social.kakao.redirectUrl}")
  private String redirectUrl;

  @Value("${social.kakao.clientId}")
  private String clientId;
  @Value("${social.kakao.clientSecret}")
  private String clientSecret;

  @Value("${social.kakao.oAuthUrl}")
  private String oAuthUrl;
  @Value("${social.kakao.userUrl}")
  private String userUrl;


  private ApiClient apiClient;


  @Autowired
  public KakaoSocialLogin(ApiClient apiClient) {
    super(SocialType.KAKAO);
    this.apiClient = apiClient;
  }


  @Override
  public void getRest(HttpServletResponse response) throws IOException {
    String url = String.format(
        oAuthUrl + "/authorize?client_id=%s&redirect_uri=%s&response_type=code",
        clientId,
        redirectUrl);

    response.sendRedirect(url);
  }

  @Override
  public String getToken(String code) {

    Map<String, String> queryParams = new HashMap<>();

    queryParams.put("client_id", clientId);
    queryParams.put("redirectUri", redirectUrl);
    queryParams.put("grant_type", "authorization_code");
    queryParams.put("code", code);

    Map response = apiClient.get(oAuthUrl + "/token", queryParams);

    if (Objects.isNull(response)) {
      throw new CustomException(SocialErrorCode.SOCIAL_INTERNAL_SERVER_ERROR);
    }

    return (String) response.get("access_token");
  }

  @Override
  public <SocialUser> SocialUser getUser(String token) {

    Map<String, String> headers = new HashMap<>();

    headers.put("Authorization", "Bearer " + token);
    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

    Map response = apiClient.get(userUrl + "/me", null, headers);

    if (Objects.isNull(response)) {
      throw new CustomException(SocialErrorCode.SOCIAL_INTERNAL_SERVER_ERROR);
    }

    String id = (String) response.get("id").toString();
    Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakaoAccount");

    String email = Optional.ofNullable(kakaoAccount)
        .map(account -> (String) account.get("email"))
        .orElse(null);

    return (SocialUser) KakaoUser.builder().id(id).email(email).build();
  }

  @Override
  public <SocialCallback> SocialCallback getRestCallback(String code) {
    String token = getToken(code);
    KakaoUser user = getUser(token);

    return (SocialCallback) KakaoCallback.builder().token(token).user(user).build();
  }
}
