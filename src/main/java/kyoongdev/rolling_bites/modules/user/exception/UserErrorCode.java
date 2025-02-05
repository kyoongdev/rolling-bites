package kyoongdev.rolling_bites.modules.user.exception;

import kyoongdev.rolling_bites.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {
  USER_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");


  private final HttpStatus statusCode;
  private final String message;
}
