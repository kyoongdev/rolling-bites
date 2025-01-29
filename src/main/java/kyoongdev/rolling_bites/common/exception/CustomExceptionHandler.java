package kyoongdev.rolling_bites.common.exception;

import io.swagger.v3.oas.annotations.Hidden;
import java.sql.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  @Hidden
  public ResponseEntity<ErrorDto> handleAllUncaughtException(Exception exception,
      WebRequest request) {
    log.error("Internal error occurred", exception);
    return new ResponseEntity<>(ErrorDto.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .timestamp(new Timestamp(System.currentTimeMillis()).toString())
        .message("INTERNAL SERVER ERROR").path(getUri()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorDto> handleCustomtException(CustomException exception,
      WebRequest request) {

    log.error("Error : ", exception);

    return new ResponseEntity<>(
        ErrorDto.builder().message(exception.getMessage()).status(exception.getStatus().value())
            .path(getUri()).timestamp(new Timestamp(System.currentTimeMillis()).toString())
            .build(),
        exception.getStatus());

  }

  private String getUri() {
    String uri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    int i = uri.indexOf('/', 7);
    return uri.substring(i);
  }


}
