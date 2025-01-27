package kyoongdev.rolling_bites.common.paging;


import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PagingResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(Paging.class);
  }


  @Override
  public PagingDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    int page = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("page")));
    int limit = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("limit")));
    PagingDto paging = new PagingDto();
    paging.setPage(page);
    paging.setLimit(limit);

    return paging;
  }

}
