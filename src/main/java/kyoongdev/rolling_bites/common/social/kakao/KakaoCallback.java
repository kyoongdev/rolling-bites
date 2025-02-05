package kyoongdev.rolling_bites.common.social.kakao;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoCallback {

  private String token;

  private KakaoUser user;

}
