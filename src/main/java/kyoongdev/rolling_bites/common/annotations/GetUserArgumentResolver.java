package kyoongdev.rolling_bites.common.annotations;

import kyoongdev.rolling_bites.modules.user.domain.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class GetUserArgumentResolver implements HandlerMethodArgumentResolver {


  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    return parameter.getParameterType().isAssignableFrom(CustomUserDetail.class)
        && parameter.hasParameterAnnotation(GetUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getPrincipal();
  }
}
