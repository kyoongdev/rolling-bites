package kyoongdev.rolling_bites.modules.auth;


import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import kyoongdev.rolling_bites.modules.auth.facade.AuthFacade;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(properties = {
    "social.kakao.redirectUrl=http://localhost:8080/callback",
    "social.kakao.clientId=kakao-client-id",
    "social.kakao.clientSecret=kakao-client-secret",
    "social.kakao.oAuthUrl=http://localhost:8080",
    "social.kakao.userUrl=http://localhost:8080"
})
public class AuthFacadeTests {

  private AuthFacade authFacade;
  private UserRepository userRepository;

  private MockWebServer mockWebServer;


  @MockitoBean
  private HttpServletResponse response;


  @BeforeEach
  void setUpMockServer() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start(8080);
  }

  @AfterEach
  void shutDownMockServer() throws IOException {
    mockWebServer.shutdown();
  }

  @Autowired
  public AuthFacadeTests(AuthFacade authFacade, UserRepository userRepository) {
    this.authFacade = authFacade;
    this.userRepository = userRepository;

  }

  @Test
  @DisplayName("카카오 로그인 테스트")
  void kakaoLoginTest() throws IOException {
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    authFacade.kakaoLogin(response);

    verify(response, times(1)).sendRedirect(anyString());
  }

  @Test
  @DisplayName("카카오 로그인 콜백 - 새로운 유저 생성")
  @Transactional
  void kakaoLoginCallbackTestWithNewUser() throws IOException {

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"access_token\": \"mock_access_token\"}")
        .setResponseCode(200));

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"id\":\"social_id\",\"kakaoAccount\":{\"email\":\"test@example.com\"}}")
        .setResponseCode(200));

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    Optional<User> isNotExists = userRepository.findBySocialId("social_id");

    Assertions.assertTrue(isNotExists.isEmpty());

    authFacade.kakaoCallback("mock_code", response);

    verify(response, times(1)).sendRedirect(anyString());

    Optional<User> isExists = userRepository.findBySocialId("social_id");

    Assertions.assertTrue(isExists.isPresent());

    User user = isExists.get();

    Assertions.assertEquals("test@example.com", user.getNickname());
    Assertions.assertEquals("social_id", user.getSocialId());
    Assertions.assertEquals(SocialType.KAKAO, user.getSocialType());


  }


  @Test
  @DisplayName("카카오 로그인 콜백 - 기존 유저")
  @Transactional
  void kakaoLoginCallbackTestWithExistingUser() throws IOException {

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"access_token\": \"mock_access_token\"}")
        .setResponseCode(200));

    mockWebServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/json")
        .setBody("{\"id\":\"social_id\",\"kakaoAccount\":{\"email\":\"test@example.com\"}}")
        .setResponseCode(200));

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    userRepository.save(User.builder().nickname("test@example.com").socialId("social_id")
        .socialType(SocialType.KAKAO).build());

    Optional<User> existingUser = userRepository.findBySocialId("social_id");

    Assertions.assertTrue(existingUser.isPresent());

    authFacade.kakaoCallback("mock_code", response);

    verify(response, times(1)).sendRedirect(anyString());

    Optional<User> isExists = userRepository.findBySocialId("social_id");

    Assertions.assertTrue(isExists.isPresent());

    User user = isExists.get();

    Assertions.assertEquals("test@example.com", user.getNickname());
    Assertions.assertEquals("social_id", user.getSocialId());
    Assertions.assertEquals(SocialType.KAKAO, user.getSocialType());


  }

}
