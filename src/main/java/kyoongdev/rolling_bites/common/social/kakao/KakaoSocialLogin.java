package kyoongdev.rolling_bites.common.social.kakao;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.common.social.SocialErrorCode;
import kyoongdev.rolling_bites.common.social.SocialLogin;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class KakaoSocialLogin extends SocialLogin {


  @Value("${social.kakao.redirectUrl}")
  private String redirectUrl;

  @Value("${social.kakao.clientId}")
  private String clientId;
  @Value("${social.kakao.clientSecret}")
  private String clientSecret;

  public KakaoSocialLogin() {
    super(SocialType.KAKAO);
  }


  @Override
  public void getRest(HttpServletResponse response) throws IOException {
    String url = String.format(
        "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
        clientId,
        redirectUrl);

    response.sendRedirect(url);
  }

  @Override
  public String getToken(String code) {
    WebClient webClient = WebClient.builder().baseUrl("https://kauth.kakao.com/oauth/token")
        .build();

    Map response = webClient.get().uri(
            uriBuilder -> uriBuilder.path("").queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId).queryParam("client_secret", clientSecret)
                .queryParam("redirectUri", redirectUrl).queryParam("code", code).build()).retrieve()
        .bodyToMono(Map.class).block();

    if (Objects.isNull(response)) {
      throw new CustomException(SocialErrorCode.SOCIAL_INTERNAL_SERVER_ERROR);
    }

    return (String) response.get("access_token");
  }

  @Override
  public <SocialUser> SocialUser getUser(String token) {
    WebClient webClient = WebClient.builder().baseUrl("https://kapi.kakao.com/v2/user/me")
        .defaultHeaders(headers -> {
          headers.setBearerAuth(token);
          headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        }).build();

    Map response = webClient.get().retrieve().bodyToMono(Map.class).block();

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
