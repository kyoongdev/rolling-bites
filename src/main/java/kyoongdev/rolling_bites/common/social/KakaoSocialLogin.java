package kyoongdev.rolling_bites.common.social;


import jakarta.servlet.http.HttpServletResponse;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.springframework.stereotype.Component;

@Component

public class KakaoSocialLogin extends SocialLogin {

  public KakaoSocialLogin() {
    super(SocialType.KAKAO);
  }


  @Override
  public void getRest(HttpServletResponse response) {

  }

  @Override
  public String getToken(String code) {
    return null;
  }

  @Override
  public <SocialUser> SocialUser getUser(String token) {
    return null;
  }

  @Override
  public <SocialCallback> SocialCallback getRestCallback(String code) {
    return null;
  }
}
