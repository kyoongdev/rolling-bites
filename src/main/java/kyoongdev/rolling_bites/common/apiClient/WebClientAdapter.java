package kyoongdev.rolling_bites.common.apiClient;

import java.net.URI;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class WebClientAdapter implements ApiClient {

  private final WebClient webClient;

  public WebClientAdapter(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public Map get(String url, Map<String, String> queryParams, Map<String, String> headers) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);

    if (queryParams != null) {
      queryParams.forEach(uriBuilder::queryParam);
    }

    URI urlResult = uriBuilder.build().toUri();

    WebClient.RequestHeadersSpec<?> request = webClient.get().uri(urlResult);

    if (headers != null) {
      request.headers(httpHeaders -> headers.forEach(httpHeaders::set));
    }

    return request.retrieve().bodyToMono(Map.class).block();
  }

}
