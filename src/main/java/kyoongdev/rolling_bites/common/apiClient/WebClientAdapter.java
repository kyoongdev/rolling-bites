package kyoongdev.rolling_bites.common.apiClient;

import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientAdapter implements ApiClientAdapter {

  private final WebClient webClient;

  public WebClientAdapter(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public Map get(Map<String, String> headers, Map<String, String> queryParams, String url) {
    WebClient.RequestHeadersSpec<?> request = webClient.get().uri(url);

    // 헤더 설정
    if (headers != null) {
      request.headers(httpHeaders -> headers.forEach(httpHeaders::set));
    }

    return request.retrieve().bodyToMono(Map.class).block();
  }

}
