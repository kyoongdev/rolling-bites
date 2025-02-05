package kyoongdev.rolling_bites.modules.user.services;


import java.util.Optional;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.dto.CommonUserDto;
import kyoongdev.rolling_bites.modules.user.dto.CreateUserDto;
import kyoongdev.rolling_bites.modules.user.dto.UpdateUserDto;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.exception.UserErrorCode;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


  public CommonUserDto findCommonUserById(Integer id) {
    User user = findUserByIdOrThrow(id);

    return CommonUserDto.builder().id(user.getId().intValue()).nickname(user.getNickname())
        .socialId(user.getSocialId()).socialType(user.getSocialType()).build();

  }

  @Transactional
  public User createUser(CreateUserDto data) {

    Optional<User> isExists = userRepository.findByNicknameOrSocialId(data.getNickname(),
        data.getSocialId());

    if (isExists.isPresent()) {
      throw new CustomException(UserErrorCode.USER_EXISTS);
    }

    return userRepository.save(data.toEntity());
  }

  @Transactional
  public User updateUser(Integer id, UpdateUserDto data) {

    User user = findUserByIdOrThrow(id);

    return userRepository.save(data.toEntity(user));

  }

  @Transactional
  public void deleteUser(Integer id) {
    User user = findUserByIdOrThrow(id);

    userRepository.delete(user);
  }

  private User findUserByIdOrThrow(Integer id) {
    return userRepository.findById(id.longValue())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
  }


}
