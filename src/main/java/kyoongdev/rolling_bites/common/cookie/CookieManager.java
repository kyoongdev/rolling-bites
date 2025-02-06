package kyoongdev.rolling_bites.common.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Objects;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.common.http.HttpUtil;
import lombok.Generated;


@Generated
public class CookieManager {


  public static HttpServletResponse getResponse() {
    return HttpUtil.getResponse();
  }

  public static void put(String key, String value, int maxAge, boolean secure) throws Exception {
    Cookie cookie = new Cookie(key, Base64.getEncoder().encodeToString(value.getBytes()));

    cookie.setMaxAge(maxAge);
    cookie.setHttpOnly(true);
    cookie.setSecure(secure);
    cookie.setPath("/");

    getResponse().addCookie(cookie);
  }

  public static String get(String key) throws Exception {
    Cookie cookie = getCookie(key);

    if (cookie == null) {
      throw new CustomException(CookieErrorCode.NOT_FOUND);
    }

    String cookieValue = cookie.getValue();
    byte[] decodedBytes = Base64.getDecoder().decode(cookieValue);

    return new String(decodedBytes);
  }

  public static Cookie[] getAllCookies() {
    return Objects.requireNonNull(HttpUtil.getRequest()).getCookies();
  }

  public static Cookie getCookie(String key) {
    Cookie[] cookies = getAllCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(key)) {
          return cookie;
        }
      }
    }
    return null;
  }

}
