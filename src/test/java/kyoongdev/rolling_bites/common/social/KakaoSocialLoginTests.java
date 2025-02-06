package kyoongdev.rolling_bites.common.social;


import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.common.social.kakao.KakaoCallback;
import kyoongdev.rolling_bites.common.social.kakao.KakaoSocialLogin;
import kyoongdev.rolling_bites.common.social.kakao.KakaoUser;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource(properties = {
    "social.kakao.redirectUrl=http://localhost:8080/callback",
    "social.kakao.clientId=kakao-client-id",
    "social.kakao.clientSecret=kakao-client-secret",
    "social.kakao.oAuthUrl=http://localhost:8080",
    "social.kakao.userUrl=http://localhost:8080"
})
class KakaoSocialLoginTests {


  private KakaoSocialLogin kakaoSocialLogin;

  private MockWebServer mockWebServer;


  @MockitoBean
  private HttpServletResponse response;

  @Autowired
  public KakaoSocialLoginTests(KakaoSocialLogin kakaoSocialLogin) {
    this.kakaoSocialLogin = kakaoSocialLogin;

  }

  @BeforeEach
  void setUpMockServer() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start(8080);
  }

  @AfterEach
  void shutDownMockServer() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  @DisplayName("OAuth Redirect URL 생성 및 호출 테스트")
  void testGetRest() throws Exception {

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    kakaoSocialLogin.getRest(response);

    verify(response, times(1)).sendRedirect(anyString());
  }

  @Test
  @DisplayName("Kakao OAuth 토큰 테스트 - 성공")
  void testGetTokenSuccess() throws IOException {
    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"access_token\": \"mock_access_token\"}")
        .setResponseCode(200));

    String token = kakaoSocialLogin.getToken("mock_code");

    Assertions.assertEquals(token, "mock_access_token");
  }


  @Test
  @DisplayName("Kakao OAuth 토큰 테스트 - 실패")
  void testGetTokenFailure() throws IOException {
    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setResponseCode(200));

    Assertions.assertThrows(CustomException.class, () -> {
      kakaoSocialLogin.getToken("mock_code");
    }, SocialErrorCode.SOCIAL_INTERNAL_SERVER_ERROR.getMessage());
  }


  @Test
  @DisplayName("카카오 사용자 정보 가져오기 테스트 - 성공")
  void testGetUser() {
    Map<String, Object> kakaoAccount = new HashMap<>();
    kakaoAccount.put("email", "test@example.com");

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("id", 123456789);
    responseMap.put("kakaoAccount", kakaoAccount);

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"id\":123456789,\"kakaoAccount\":{\"email\":\"test@example.com\"}}")
        .setResponseCode(200));

    KakaoUser user = kakaoSocialLogin.getUser("mock_token");
    Assertions.assertEquals("123456789", user.getId());
    Assertions.assertEquals("test@example.com", user.getEmail());
  }

  @Test
  @DisplayName("카카오 사용자 정보 가져오기 테스트 - 실패")
  void testGetUserFailure() {

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")

        .setResponseCode(200));

    Assertions.assertThrows(CustomException.class, () -> {
      kakaoSocialLogin.getUser("mock_token");
    }, SocialErrorCode.SOCIAL_INTERNAL_SERVER_ERROR.getMessage());

  }

  //
  @Test
  @DisplayName("카카오 OAuth Callback 테스트")
  void testGetRestCallback() {
    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"access_token\": \"mock_access_token\"}")
        .setResponseCode(200));

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"id\":123456789,\"kakaoAccount\":{\"email\":\"test@example.com\"}}")
        .setResponseCode(200));

    KakaoCallback callback = kakaoSocialLogin.getRestCallback("auth_code");

    Assertions.assertEquals("mock_access_token", callback.getToken());
    Assertions.assertEquals("123456789", callback.getUser().getId());
    Assertions.assertEquals("test@example.com", callback.getUser().getEmail());
  }

}
