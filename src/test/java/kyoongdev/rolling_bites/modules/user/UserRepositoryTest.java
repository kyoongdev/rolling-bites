package kyoongdev.rolling_bites.modules.user;


import java.util.Optional;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@RepositoryTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("유저 생성 및 조회 테스트")
  void createAndFindUser() {
    SocialType socialType = SocialType.valueOf("KAKAO");
    User user = User.builder().nickname("유저1").socialId("123123")
        .socialType(socialType).build();

    User newUser = userRepository.save(user);

    Assertions.assertEquals(newUser, user);
  }

  @Test
  @DisplayName("유저 소셜 아이디 조회 테스트")
  void findUserBySocialId() {
    SocialType socialType = SocialType.valueOf("KAKAO");
    User newUser = User.builder().nickname("유저1").socialId("123123")
        .socialType(socialType).build();

    userRepository.save(newUser);

    Optional<User> user = userRepository.findUserBySocialId("123123");

    Assertions.assertTrue(user.isPresent());
    Assertions.assertEquals("123123", user.get().getSocialId());
    Assertions.assertEquals(user.get().getSocialType(), SocialType.KAKAO.getSocialType());
  }


  @Test
  @DisplayName("유저 닉네입 조회 테스트")
  void findUserByNickname() {

    SocialType socialType = SocialType.valueOf("KAKAO");
    User newUser = User.builder().nickname("유저1").socialId("123123")
        .socialType(socialType).build();

    userRepository.save(newUser);

    Optional<User> user = userRepository.findUserByNickname("유저1");

    Assertions.assertTrue(user.isPresent());
    Assertions.assertEquals("유저1", user.get().getNickname());


  }
}
