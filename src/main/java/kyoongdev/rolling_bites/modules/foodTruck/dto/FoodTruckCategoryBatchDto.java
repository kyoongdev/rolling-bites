package kyoongdev.rolling_bites.modules.foodTruck.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FoodTruckCategoryBatchDto {

  private Long foodTruckId;

  private Long categoryId;

}
