package kyoongdev.rolling_bites.modules.foodTruck.dto.repository;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindFoodTrucksWhere {

  private String name;
  private Long smallRegionId;

  private Long categoryId;
  private String lat;
  private String lng;

}
