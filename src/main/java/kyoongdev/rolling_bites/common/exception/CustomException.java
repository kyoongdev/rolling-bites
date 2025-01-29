package kyoongdev.rolling_bites.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
  
  private final HttpStatus status;
  private final String message;


  public CustomException(ErrorCode errorCode) {
    this.status = errorCode.getStatusCode();
    this.message = errorCode.getMessage();
  }

  public CustomException(String message, HttpStatus status) {
    this.status = status;
    this.message = message;
  }

  public CustomException(Exception exception) {
    if (exception.getClass() == CustomException.class) {
      CustomException customException = (CustomException) exception;
      this.status = customException.getStatus();
      this.message = customException.getMessage();
    } else {
      this.status = HttpStatus.INTERNAL_SERVER_ERROR;
      this.message = "INTERNAL_SERVER_ERROR";
    }
  }
}
