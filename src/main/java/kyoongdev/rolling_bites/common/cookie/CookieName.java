package kyoongdev.rolling_bites.common.cookie;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CookieName {

  ACCESS_TOKEN("roolling-bites-access-token", 60 * 60 * 24),
  REFRESH_TOKEN("roolling-bites-refresh-token", 60 * 60 * 24 * 14);

  private final String name;
  private final Integer age;
}
