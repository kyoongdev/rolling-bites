package kyoongdev.rolling_bites.common.social;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;

public abstract class SocialLogin {

  protected SocialType socialType;


  public SocialLogin(SocialType socialType) {
    this.socialType = socialType;
  }


  public abstract void getRest(HttpServletResponse response) throws IOException;

  public abstract String getToken(String code);

  public abstract <SocialUser> SocialUser getUser(String token);

  public abstract <SocialCallback> SocialCallback getRestCallback(String code);

}
