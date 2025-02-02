package kyoongdev.rolling_bites.modules.foodTruck.batch;

import java.util.ArrayList;
import java.util.List;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckRegionBatchDto;
import org.springframework.stereotype.Component;


@Component
public class FoodTruckRegionBatch implements BatchInsert<FoodTruckRegionBatchDto> {

  @Override
  public List<FoodTruckRegionBatchDto> getData() {
    List<FoodTruckRegionBatchDto> data = new ArrayList<>();

    return data;
  }


  @Override
  public void executeBatch(List<FoodTruckRegionBatchDto> subDataList) {

  }
}
