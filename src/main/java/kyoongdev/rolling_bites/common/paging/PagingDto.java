package kyoongdev.rolling_bites.common.paging;


import lombok.Data;

@Data
public class PagingDto {


  Integer page;

  Integer limit;


  public Integer getOffset() {
    return (this.page - 1) * this.limit;
  }

  
}
