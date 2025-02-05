package kyoongdev.rolling_bites.modules.user.enums;


import lombok.Getter;

@Getter
public enum SocialType {

  KAKAO(1),
  GOOGLE(2);

  private final Integer socialType;


  SocialType(Integer socialType) {
    this.socialType = socialType;
  }
}
