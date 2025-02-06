package kyoongdev.rolling_bites.common.apiClient;

import java.util.Map;


//TODO: Post, Put, Patch, Delete 추가
public interface ApiClient {

  Map get(String url, Map<String, String> queryParams, Map<String, String> headers);

  default Map get(String url, Map<String, String> queryParams) {
    return get(url, queryParams, null);
  }

  default Map get(String url) {
    return get(url, null, null);
  }

}
