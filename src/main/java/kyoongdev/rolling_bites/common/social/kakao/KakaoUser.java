package kyoongdev.rolling_bites.common.social.kakao;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoUser {

  private String id;
  private String email;

}