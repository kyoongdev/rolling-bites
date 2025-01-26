package kyoongdev.rolling_bites.modules.user;


import java.util.Optional;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRespositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("유저 생성 및 조회 테스트")
  void createAndFindUser() {
    SocialType socialType = SocialType.valueOf("KAKAO");
    User user = User.builder().nickname("유저1").socialId("123123")
        .socialType(socialType.getSocialType()).build();

    User newUser = userRepository.save(user);

    Assertions.assertEquals(newUser, user);
  }

  @Test
  @DisplayName("유저 소셜 아이디 조회 테스트")
  void findUserBySocialId() {
    SocialType socialType = SocialType.valueOf("KAKAO");
    User newUser = User.builder().nickname("유저1").socialId("123123")
        .socialType(socialType.getSocialType()).build();

    userRepository.save(newUser);

    Optional<User> user = userRepository.findUserBySocialId("123123");

    Assertions.assertEquals(user.isPresent(), true);
    Assertions.assertEquals(user.get().getSocialId(), "123123");
    Assertions.assertEquals(user.get().getSocialType(), SocialType.KAKAO.getSocialType());
  }
}
