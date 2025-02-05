package kyoongdev.rolling_bites.common.social;


import java.util.Objects;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocialFactoryTest {


  private SocialFactory socialFactory;


  @Autowired
  public SocialFactoryTest(SocialFactory socialFactory) {
    this.socialFactory = socialFactory;

  }


  @Test
  @DisplayName("소셜 로그인 불러오기 - 성공")
  void 소셜_로그인_불러오기_성공() {
    SocialLogin socialLogin = socialFactory.getSocialLogin(SocialType.KAKAO);

    Assertions.assertTrue(Objects.nonNull(socialLogin));

  }

  @Test
  @DisplayName("소셜 로그인 불러오기 - 실패")
  void 소셜_로그인_불러오기_실패() {
    Assertions.assertThrows(CustomException.class, () -> {
      socialFactory.getSocialLogin(SocialType.GOOGLE);
    }, SocialErrorCode.NOT_EXISTS.getMessage());
  }

}
