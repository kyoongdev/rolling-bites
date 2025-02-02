package kyoongdev.rolling_bites.modules.foodTruck.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FoodTruckBatchDto {

  private String name;
  private Integer openAt;
  private Integer closeAt;

  private Long regionId;


}
