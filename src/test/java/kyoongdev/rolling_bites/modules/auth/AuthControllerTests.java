package kyoongdev.rolling_bites.modules.auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class AuthControllerTests {


  @Test
  @DisplayName("카카오 로그인 URL로 Redirect 테스트")
  @WithMockUser
  void testKakaoLoginRedirect() throws Exception {
    given()
        .when()
        .redirects().follow(false) // 리다이렉트 방지
        .get("/auth/social/kakao")
        .then()
        .statusCode(302)
        .header("Location", containsString("https://kauth.kakao.com/oauth/authorize"));
  }


}


