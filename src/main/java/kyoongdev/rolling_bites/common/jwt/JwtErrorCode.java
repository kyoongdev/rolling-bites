package kyoongdev.rolling_bites.common.jwt;


import kyoongdev.rolling_bites.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum JwtErrorCode implements ErrorCode {

  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다.");

  private final HttpStatus statusCode;
  private final String message;

}
