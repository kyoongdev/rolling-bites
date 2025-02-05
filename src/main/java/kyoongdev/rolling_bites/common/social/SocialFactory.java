package kyoongdev.rolling_bites.common.social;

import java.util.HashMap;
import java.util.Map;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialFactory {

  private Map<SocialType, SocialLogin> socialLogins = new HashMap<>();


  @Autowired
  public SocialFactory() {

  }


}
