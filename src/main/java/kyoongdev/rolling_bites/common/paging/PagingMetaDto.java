package kyoongdev.rolling_bites.common.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "PageMetaDTO")
public class PagingMetaDto {

  @Schema(description = "총 개수")
  private int total;

  @Schema(description = "현재 페이지")
  private int page;

  @Schema(description = "데이터 개수")
  private int limit;

  @Schema(description = "이전 페이지 존재 유무")
  private boolean hasPrev;
  @Schema(description = "다음 페이지 존재 유무")
  private boolean hasNext;


  static PagingMetaDto fromPagingDTO(PagingDto paging, int count) {
    int total = count;
    int page = paging.getPage();
    int limit = paging.getLimit();
    boolean hasPrev = page > 1;
    boolean hasNext = page * limit < count;
    
    return new PagingMetaDto(total, page, limit, hasPrev, hasNext);
  }

}
