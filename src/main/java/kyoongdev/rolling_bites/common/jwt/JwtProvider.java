package kyoongdev.rolling_bites.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.crypto.SecretKey;
import kyoongdev.rolling_bites.common.cookie.CookieManager;
import kyoongdev.rolling_bites.common.cookie.CookieName;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.modules.user.domain.CustomUserDetail;
import kyoongdev.rolling_bites.modules.user.services.CustomUserDetailService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {


  private final CustomUserDetailService customUserDetailService;


  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access_token_expires_in}")
  private long accessTokenExpiresIn;
  @Value("${jwt.refresh_token_expires_in}")
  private long refreshTokenExpiresIn;


  public TokenDto createAccessTokenAndRefreshToken(String id) {
    Map<String, Object> params = new HashMap<>();
    params.put("id", id);

    String accessToken = generateToken(params, accessTokenExpiresIn);
    String refreshToken = generateToken(params, refreshTokenExpiresIn);

    return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }


  public String generateToken(Map<String, Object> params, long expiresIn) {
    Claims claims = Jwts.claims();
    claims.putAll(params);

    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime tokenValidity = now.plusSeconds(expiresIn);

    return Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .setClaims(claims).setExpiration(Date.from(tokenValidity.toInstant()))
        .signWith(getSigningKey()).compact();
  }

  public Claims validateToken(String token) {
    try {

      Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey())
          .setAllowedClockSkewSeconds(0)
          .build().parseClaimsJws(token)
          .getBody();

      if (claims.getExpiration().before(Date.from(ZonedDateTime.now().toInstant()))) {
        throw new CustomException(JwtErrorCode.EXPIRED_TOKEN);
      }

      return claims;
    } catch (ExpiredJwtException e) {

      throw new CustomException(JwtErrorCode.EXPIRED_TOKEN);
    } catch (JwtException e) {
      throw new CustomException(JwtErrorCode.INVALID_TOKEN);
    }
  }


  @Generated
  public Optional<String> resolveToken() throws JwtException {
    String bearerToken = CookieManager.get(CookieName.ACCESS_TOKEN.getName());

    return Optional.of(bearerToken.substring(7));
  }


  public Claims compareToken(TokenDto token) {
    Claims accessTokenClaims = validateToken(token.getAccessToken());
    Claims refreshTokenClaims = validateToken(token.getRefreshToken());

    accessTokenClaims.keySet().forEach(key -> {
      if (!key.equals("exp") && !accessTokenClaims.get(key).equals(refreshTokenClaims.get(key))) {
        throw new CustomException(JwtErrorCode.INVALID_TOKEN);
      }
    });

    return refreshTokenClaims;
  }

  public Authentication getAuthentication(String subject) {

    CustomUserDetail userDetails = customUserDetailService.loadUserByUsername(subject);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
