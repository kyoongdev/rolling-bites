package kyoongdev.rolling_bites.common.apiClient;

import java.net.URI;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

public class WebClientAdapter implements ApiClientAdapter {

  private final WebClient webClient;

  public WebClientAdapter(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public Map get(String url, Map<String, String> headers, Map<String, String> queryParams) {
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
