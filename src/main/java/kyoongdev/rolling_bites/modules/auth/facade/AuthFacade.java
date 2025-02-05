package kyoongdev.rolling_bites.modules.auth.facade;

import kyoongdev.rolling_bites.common.social.SocialFactory;
import kyoongdev.rolling_bites.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

  private final UserService userService;
  private final SocialFactory socialFactory;


}
