package kyoongdev.rolling_bites.modules.user.dto;

import kyoongdev.rolling_bites.common.dto.UpdateDto;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class UpdateUserDto extends UpdateDto<User> {

  private String nickname;
  private String socialId;
  private SocialType socialType;


  @Override
  public User toEntity(User user) {

    myCopyProperties(user);
    System.out.println(user.getNickname());
    System.out.println(this.nickname);
    return user;

  }


}
