package kyoongdev.rolling_bites.common.apiClient;

import java.util.Map;


//TODO: Post, Put, Patch, Delete 추가
public interface ApiClientAdapter {

  Map get(Map<String, String> headers, Map<String, String> queryParams, String url);

}
