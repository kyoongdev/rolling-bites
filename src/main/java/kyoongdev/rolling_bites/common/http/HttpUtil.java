package kyoongdev.rolling_bites.common.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Generated
public class HttpUtil {

  public static HttpServletRequest getRequest() {
    try {
      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    } catch (Exception e) {
      return null;
    }
  }


  public static HttpServletResponse getResponse() {
    try {
      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    } catch (Exception e) {
      return null;
    }
  }


}
