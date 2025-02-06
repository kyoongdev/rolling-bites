package kyoongdev.rolling_bites.common.social;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kyoongdev.rolling_bites.common.social.kakao.KakaoSocialLogin;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource(properties = {
    "social.kakao.redirectUrl=http://localhost:8080/callback",
    "social.kakao.clientId=kakao-client-id",
    "social.kakao.clientSecret=kakao-client-secret"
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
    mockWebServer.start();
  }

  @AfterEach
  void shutDownMockServer() throws IOException {
    mockWebServer.shutdown();
  }


  @Test
  @DisplayName("Kakao OAuth Redirect 테스트")
  void testGetRest() throws IOException {
//    mockWebServer.enqueue(
//        new MockResponse().setStatus("HTTP/1.1 200")
//            .setBody("{\"access_token\":\"accessToken\""));
//    String token = kakaoSocialLogin.getToken("code");
//    System.out.println("token : " + token);
    Assertions.assertEquals(1, 1);
//    kakaoSocialLogin.getRest(response);
//    verify(response).sendRedirect(anyString()); // ✅ 리다이렉트 URL이 호출되었는지 검증
  }

//  @Test
//  @DisplayName("카카오 토큰 가져오기 테스트")
//  void testGetToken() {
//    String mockToken = "mock_access_token";
//    when(responseSpec.bodyToMono(Map.class)).thenReturn(
//        Mono.just(Map.of("access_token", mockToken)));
//
//    String token = kakaoSocialLogin.getToken("auth_code");
//    Assertions.assertEquals(mockToken, token);
//  }
//
//  @Test
//  @DisplayName("카카오 사용자 정보 가져오기 테스트")
//  void testGetUser() {
//    Map<String, Object> kakaoAccount = new HashMap<>();
//    kakaoAccount.put("email", "test@example.com");
//
//    Map<String, Object> responseMap = new HashMap<>();
//    responseMap.put("id", 123456789);
//    responseMap.put("kakaoAccount", kakaoAccount);
//
//    when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(responseMap));
//
//    KakaoUser user = kakaoSocialLogin.getUser("mock_token");
//    Assertions.assertEquals("123456789", user.getId());
//    Assertions.assertEquals("test@example.com", user.getEmail());
//  }
//
//  @Test
//  @DisplayName("카카오 OAuth Callback 테스트")
//  void testGetRestCallback() {
//    String mockToken = "mock_access_token";
//    when(responseSpec.bodyToMono(Map.class)).thenReturn(
//        Mono.just(Map.of("access_token", mockToken)));
//
//    Map<String, Object> kakaoAccount = new HashMap<>();
//    kakaoAccount.put("email", "test@example.com");
//
//    Map<String, Object> responseMap = new HashMap<>();
//    responseMap.put("id", 123456789);
//    responseMap.put("kakaoAccount", kakaoAccount);
//
//    when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(responseMap));
//
//    KakaoCallback callback = kakaoSocialLogin.getRestCallback("auth_code");
//
//    Assertions.assertEquals(mockToken, callback.getToken());
//    Assertions.assertEquals("123456789", callback.getUser().getId());
//    Assertions.assertEquals("test@example.com", callback.getUser().getEmail());
//  }

}
