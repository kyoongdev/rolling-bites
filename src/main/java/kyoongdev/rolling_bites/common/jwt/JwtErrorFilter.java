package kyoongdev.rolling_bites.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import kyoongdev.rolling_bites.common.exception.CustomException;
import kyoongdev.rolling_bites.common.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtErrorFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  public JwtErrorFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (CustomException e) {

      ErrorDto error = ErrorDto.builder().status(e.getStatus().value())
          .message(e.getMessage()).path(request.getPathInfo())
          .timestamp(new Timestamp(System.currentTimeMillis()).toString())
          .build();
      log.info(objectMapper.writeValueAsString(error));
      
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Methods", "*");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers",
          "Origin, X-Requested-With, Content-Type, Accept, Authorization");

      response.setHeader("Content-Type", "*");
      response.setStatus(error.status());
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      objectMapper.writeValue(response.getWriter(), error);


    }
  }
}
