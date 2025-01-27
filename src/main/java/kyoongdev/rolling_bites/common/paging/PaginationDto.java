package kyoongdev.rolling_bites.common.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Schema(description = "페이지네이션 DTO")
public class PaginationDto<T> {


  @Schema(description = "데이터")
  private List<T> data;


  @Schema(description = "페이지 정보")
  private PagingMetaDto paging;


  public static <T> PaginationDto<T> of(Page<T> data, PagingDto paging, Long count) {
    PaginationDto<T> pagination = new PaginationDto<>();

    pagination.setData(data.getContent());
    pagination.setPaging(PagingMetaDto.fromPagingDTO(paging, count.intValue()));

    return pagination;
  }
}
