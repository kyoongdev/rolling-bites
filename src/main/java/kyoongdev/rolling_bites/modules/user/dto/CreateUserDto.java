package kyoongdev.rolling_bites.modules.user.dto;


import kyoongdev.rolling_bites.common.dto.CreateDto;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserDto implements CreateDto<User> {

  private String nickname;
  private String socialId;
  private SocialType socialType;


  @Override
  public User toEntity() {
    return User.builder().nickname(nickname).socialId(socialId).socialType(socialType).build();
  }
}
