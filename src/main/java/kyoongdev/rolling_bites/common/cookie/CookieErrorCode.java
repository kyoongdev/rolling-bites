package kyoongdev.rolling_bites.common.cookie;

import kyoongdev.rolling_bites.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum CookieErrorCode implements ErrorCode {

  NOT_FOUND(HttpStatus.NOT_FOUND, "쿠키 값을 찾을 수 없습니다.");

  private final HttpStatus statusCode;
  private final String message;
}
