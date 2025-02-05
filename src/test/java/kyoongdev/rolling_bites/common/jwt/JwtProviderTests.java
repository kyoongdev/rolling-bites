package kyoongdev.rolling_bites.common.jwt;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class JwtProviderTests {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @Autowired
  public JwtProviderTests(JwtProvider jwtProvider, UserRepository userRepository) {
    this.jwtProvider = jwtProvider;
    this.userRepository = userRepository;
  }


  @Test
  @DisplayName("토큰 생성 테스트")
  void createTokenTest() {
    Map<String, Object> params = new HashMap<>();
    params.put("id", "Test");
    String token = jwtProvider.generateToken(params, 300000);

    Assertions.assertFalse(token.isEmpty());

    TokenDto tokens = jwtProvider.createAccessTokenAndRefreshToken("id");

    Assertions.assertFalse(tokens.getRefreshToken().isEmpty());
    Assertions.assertFalse(tokens.getAccessToken().isEmpty());
    Assertions.assertNotSame(tokens.getRefreshToken(), tokens.getAccessToken());


  }

  @Test
  @DisplayName("토근 검증 테스트 - 성공")
  void validateTokenTestSuccess() {
    TokenDto tokens = jwtProvider.createAccessTokenAndRefreshToken("123");

    Claims accessTokenClaims = jwtProvider.validateToken(tokens.getAccessToken());
    Claims refreshTokenClaims = jwtProvider.validateToken(tokens.getRefreshToken());

    Assertions.assertEquals("123", accessTokenClaims.get("id"));
    Assertions.assertEquals("123", refreshTokenClaims.get("id"));


  }

  @Test
  @DisplayName("토근 검증 테스트 - 실패 ()")
  void validateTokenTestFailure() throws InterruptedException {

    Assertions.assertThrows(CustomException.class, () -> {
      jwtProvider.validateToken("test");
    }, JwtErrorCode.INVALID_TOKEN.getMessage());


  }

  @Test
  @DisplayName("토큰 검증 테스트 - 실패 (만료된 토큰)")
  void validateTokenExpiredTest() {
    Map<String, Object> params = new HashMap<>();
    params.put("id", "123");

    String token = jwtProvider.generateToken(params, -2000000);

    Assertions.assertThrows(CustomException.class, () -> jwtProvider.validateToken(token),
        JwtErrorCode.EXPIRED_TOKEN.getMessage());
  }

  @Test
  @DisplayName("토큰 비교 테스트 - 성공")
  void compareTokenTestSuccess() {
    TokenDto tokens = jwtProvider.createAccessTokenAndRefreshToken("123");

    Claims result = jwtProvider.compareToken(tokens);

    Assertions.assertEquals("123", result.get("id"));
  }


  @Test
  @DisplayName("토큰 비교 테스트 - 실패 (다른 토큰)")
  void compareTokenTestFailure() {
    TokenDto tokens = jwtProvider.createAccessTokenAndRefreshToken("123");
    TokenDto secondTokens = jwtProvider.createAccessTokenAndRefreshToken("1234");

    Assertions.assertThrows(CustomException.class, () -> {
      jwtProvider.compareToken(TokenDto.builder().accessToken(tokens.getAccessToken())
          .refreshToken(secondTokens.getRefreshToken()).build());
    });
  }

  @Test
  @DisplayName("토큰 비교 테스트 - 실패 (다른 key-value 존재)")
  void compareTokenTestDifferentKeyFailure() {
    Map<String, Object> params1 = new HashMap<>();
    params1.put("id", "123");
    params1.put("role", "USER");

    Map<String, Object> params2 = new HashMap<>();
    params2.put("id", "123");
    params2.put("role", "ADMIN"); // ✅ role이 다름

    String accessToken = jwtProvider.generateToken(params1, 300000);
    String refreshToken = jwtProvider.generateToken(params2, 300000);

    TokenDto tokens = TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken)
        .build();

    Assertions.assertThrows(CustomException.class, () -> jwtProvider.compareToken(tokens));
  }


  @Test
  @DisplayName("getAuthentication - 성공")
  @Transactional
  void getAuthenticationTestSuccess() {
    User user = userRepository.save(
        User.builder().nickname("hi2").socialId("123123").socialType(SocialType.KAKAO).build());

    Authentication auth = jwtProvider.getAuthentication(user.getId().toString());

    Assertions.assertEquals(user.getId().toString(), auth.getName());
  }

  @Test
  @DisplayName("getAuthentication - 실패 (사용자 없음)")
  void getAuthenticationTestFailure() {

    Assertions.assertThrows(UsernameNotFoundException.class, () -> {
      jwtProvider.getAuthentication("123");
    });
  }

  @Test
  @DisplayName("resolveToken - 성공")
  void resolveTokenTestSuccess() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer testToken");

    Optional<String> token = jwtProvider.resolveToken(request);

    Assertions.assertTrue(token.isPresent());
    Assertions.assertEquals("testToken", token.get());
  }

  @Test
  @DisplayName("resolveToken - 실패 (Authorization 헤더 없음)")
  void resolveTokenTestFailure() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn(null);

    Optional<String> token = jwtProvider.resolveToken(request);

    Assertions.assertFalse(token.isPresent());
  }

}
