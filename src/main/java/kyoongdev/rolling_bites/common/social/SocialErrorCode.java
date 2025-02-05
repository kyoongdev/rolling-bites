package kyoongdev.rolling_bites.common.social;

import kyoongdev.rolling_bites.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum SocialErrorCode implements ErrorCode {
  SOCIAL_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "소셜 로그인 중 에러가 발생했습니다."),
  NOT_EXISTS(HttpStatus.NOT_FOUND, "해당 소셜 로그인이 존재하지 않습니다.");

  private final HttpStatus statusCode;
  private final String message;
}
