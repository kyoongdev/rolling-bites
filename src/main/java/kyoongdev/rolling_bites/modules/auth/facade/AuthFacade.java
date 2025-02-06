package kyoongdev.rolling_bites.modules.auth.facade;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import kyoongdev.rolling_bites.common.jwt.JwtProvider;
import kyoongdev.rolling_bites.common.jwt.TokenDto;
import kyoongdev.rolling_bites.common.social.SocialFactory;
import kyoongdev.rolling_bites.common.social.SocialLogin;
import kyoongdev.rolling_bites.common.social.kakao.KakaoCallback;
import kyoongdev.rolling_bites.common.social.kakao.KakaoUser;
import kyoongdev.rolling_bites.modules.user.dto.CommonUserDto;
import kyoongdev.rolling_bites.modules.user.dto.CreateUserDto;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.enums.SocialType;
import kyoongdev.rolling_bites.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class AuthFacade {

  private final UserService userService;
  private final SocialFactory socialFactory;

  private final JwtProvider jwtProvider;

  @Value("${client.url}")
  private String clientUrl;


  public void kakaoLogin(HttpServletResponse response) throws IOException {

    SocialLogin socialLogin = socialFactory.getSocialLogin(SocialType.KAKAO);

    socialLogin.getRest(response);
  }


  @Transactional
  public void kakaoCallback(String code, HttpServletResponse response) throws IOException {
    SocialLogin socialLogin = socialFactory.getSocialLogin(SocialType.KAKAO);

    KakaoCallback kakakoCallback = socialLogin.getRestCallback(code);
    KakaoUser user = kakakoCallback.getUser();

    Optional<CommonUserDto> isExists = userService.checkCommonUserBySocialId(user.getId());

    String userId = isExists.map(CommonUserDto::getId)
        .map(Object::toString)
        .orElseGet(() -> {
          User newUser = userService.createUser(
              CreateUserDto.builder()
                  .nickname(user.getEmail())
                  .socialType(SocialType.KAKAO)
                  .socialId(user.getId())
                  .build()
          );
          return newUser.getId().toString();
        });

    TokenDto token = jwtProvider.createAccessTokenAndRefreshToken(userId);

    String redirectUri = UriComponentsBuilder.fromUriString(clientUrl + "/auth/kakao/success")
        .replaceQueryParam("accessToken", token.getAccessToken())
        .replaceQueryParam("refreshToken", token.getRefreshToken())
        .build().toUriString();

    response.sendRedirect(redirectUri);
  }


}
