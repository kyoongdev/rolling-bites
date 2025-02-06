package kyoongdev.rolling_bites.common.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    Optional<String> token = jwtProvider.resolveToken();
    if (token.isPresent()) {

      Authentication auth = jwtProvider.getAuthentication(
          jwtProvider.validateToken(token.get()).getSubject()
      );

      SecurityContextHolder.getContext().setAuthentication(auth);
      filterChain.doFilter(request, response);


    } else {
      filterChain.doFilter(request, response);
    }


  }

}
