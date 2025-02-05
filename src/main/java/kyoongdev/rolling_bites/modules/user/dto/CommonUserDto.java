package kyoongdev.rolling_bites.modules.user.dto;


import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonUserDto {

  private Integer id;

  private String nickname;

  private String socialId;

  private SocialType socialType;


}
