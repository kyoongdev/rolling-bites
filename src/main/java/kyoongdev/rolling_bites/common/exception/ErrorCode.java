package kyoongdev.rolling_bites.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  public HttpStatus getStatusCode();

  public String getMessage();

}
