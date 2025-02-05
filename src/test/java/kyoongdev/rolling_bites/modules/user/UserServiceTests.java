package kyoongdev.rolling_bites.modules.user;


import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.dto.CommonUserDto;
import kyoongdev.rolling_bites.modules.user.dto.CreateUserDto;
import kyoongdev.rolling_bites.modules.user.dto.UpdateUserDto;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.exception.UserErrorCode;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import kyoongdev.rolling_bites.modules.user.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceTests {

  private final UserRepository userRepository;
  private final UserService userService;


  @Autowired
  public UserServiceTests(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Test
  @DisplayName("유저 조회 테스트 - 성공")
  @Transactional
  void findUserTestSuccess() {

    User newUser = userRepository.save(
        User.builder().nickname("test1").socialType(SocialType.KAKAO).socialId("socialId").build());

    CommonUserDto user = userService.findCommonUserById(newUser.getId().intValue());

    Assertions.assertEquals(newUser.getId().intValue(), user.getId());
    Assertions.assertEquals(newUser.getNickname(), user.getNickname());
    Assertions.assertEquals(newUser.getSocialType(), user.getSocialType());
    Assertions.assertEquals(newUser.getSocialId(), user.getSocialId());
  }

  @Test
  @DisplayName("유저 조회 테스트 - 실패 (존재하지 않는 유저)")
  @Transactional
  void findUserTestFailure() {

    Assertions.assertThrows(CustomException.class, () -> userService.findCommonUserById(123),
        UserErrorCode.USER_NOT_FOUND.getMessage());

  }

  @Test
  @DisplayName("유저 생성 테스트 - 성공")
  @Transactional
  void 유저_생성_테스트_성공() {
    CreateUserDto data = CreateUserDto.builder().nickname("user1").socialId("123123")
        .socialType(SocialType.KAKAO).build();

    User newUser = userService.createUser(data);

    Assertions.assertEquals(data.getNickname(), newUser.getNickname());
    Assertions.assertEquals(data.getSocialId(), newUser.getSocialId());
    Assertions.assertEquals(data.getSocialType(), newUser.getSocialType());


  }


  @Test
  @DisplayName("유저 생성 테스트 - 실패 (존재하는 닉네임)")
  @Transactional
  void 유저_생성_테스트_이미_존재하는_닉네임() {
    CreateUserDto data = CreateUserDto.builder().nickname("user1").socialId("123123")
        .socialType(SocialType.KAKAO).build();

    User newUser = userRepository.save(
        User.builder().nickname("user1").socialType(SocialType.KAKAO).socialId("socialId").build());

    Assertions.assertThrows(CustomException.class, () -> {
      userService.createUser(data);
    }, UserErrorCode.USER_EXISTS.getMessage());


  }

  @Test
  @DisplayName("유저 생성 테스트 - 실패 (존재하는 소셜아이디)")
  @Transactional
  void 유저_생성_테스트_이미_존재하는_소셜_아이디() {
    CreateUserDto data = CreateUserDto.builder().nickname("user1").socialId("123123")
        .socialType(SocialType.KAKAO).build();

    User newUser = userRepository.save(
        User.builder().nickname("teset1").socialType(SocialType.KAKAO).socialId("123123").build());

    Assertions.assertThrows(CustomException.class, () -> {
      userService.createUser(data);
    }, UserErrorCode.USER_EXISTS.getMessage());


  }

  @Test
  @DisplayName("유저 수정 테스트 - 성공")
  @Transactional
  void 유저_수정_테스트_성공() {
    User newUser = userRepository.save(
        User.builder().nickname("teset1").socialType(SocialType.KAKAO).socialId("123123").build());

    UpdateUserDto data = UpdateUserDto.builder().nickname("수정됨").build();

    User updatedUser = userService.updateUser(newUser.getId().intValue(), data);

    Assertions.assertEquals("수정됨", updatedUser.getNickname());
    Assertions.assertEquals("123123", updatedUser.getSocialId());
  }


  @Test
  @DisplayName("유저 수정 테스트 - 실패 (유저가 없는 경우)")
  @Transactional
  void 유저_수정_테스트_실패() {

    Assertions.assertThrows(CustomException.class, () -> {
      userService.updateUser(123123, UpdateUserDto.builder().build());
    }, UserErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("유저 삭제 테스트 - 성공")
  @Transactional
  void 유저_삭제_테스트_성공() {
    User newUser = userRepository.save(
        User.builder().nickname("teset1").socialType(SocialType.KAKAO).socialId("123123").build());

    UpdateUserDto data = UpdateUserDto.builder().nickname("수정됨").build();

    userService.deleteUser(newUser.getId().intValue());

    Assertions.assertThrows(CustomException.class, () -> {
      userService.findCommonUserById(newUser.getId().intValue());
    }, UserErrorCode.USER_NOT_FOUND.getMessage());
  }


  @Test
  @DisplayName("유저 삭제 테스트 - 실패 (유저가 없는 경우)")
  @Transactional
  void 유저_삭제_테스트_실패() {

    Assertions.assertThrows(CustomException.class, () -> {
      userService.deleteUser(123123);
    }, UserErrorCode.USER_NOT_FOUND.getMessage());
  }


}
