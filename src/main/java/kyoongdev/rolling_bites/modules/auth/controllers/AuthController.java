package kyoongdev.rolling_bites.modules.auth.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kyoongdev.rolling_bites.modules.auth.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증")
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthFacade authFacade;

  @GetMapping("social/kakao/callback")
  public void kakaoCallback(@RequestParam("code") String code, HttpServletResponse response)
      throws IOException {
    authFacade.kakaoCallback(code, response);


  }

  @GetMapping("social/kakao")
  public void kakaoLogin(HttpServletResponse response) throws IOException {
    authFacade.kakaoLogin(response);
  }


}
