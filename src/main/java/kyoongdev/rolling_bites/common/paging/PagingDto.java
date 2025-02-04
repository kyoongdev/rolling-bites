package kyoongdev.rolling_bites.common.paging;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingDto {


  Integer page;

  Integer limit;


  public Integer getOffset() {
    return (this.page - 1) * this.limit;
  }


}
