package kyoongdev.rolling_bites.common.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialFactory {

  private Map<SocialType, SocialLogin> socialLogins = new HashMap<>();


  @Autowired
  public SocialFactory(List<SocialLogin> socialLogins) {

    for (SocialLogin socialLogin : socialLogins) {
      this.socialLogins.put(socialLogin.socialType, socialLogin);
    }
  }


  public SocialLogin getSocialLogin(SocialType socialType) {

    if (!isExists(socialType)) {
      throw new CustomException(SocialErrorCode.NOT_EXISTS);
    }

    return socialLogins.get(socialType);

  }

  private boolean isExists(SocialType socialType) {
    return socialLogins.containsKey(socialType);
  }


}
