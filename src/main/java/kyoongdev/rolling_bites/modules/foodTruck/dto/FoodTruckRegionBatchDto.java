package kyoongdev.rolling_bites.modules.foodTruck.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FoodTruckRegionBatchDto {

  private String lat;
  private String lng;
  private String name;
  private Long smallRegionId;


}
