package kyoongdev.rolling_bites.modules.foodTruck.repository;

import java.util.List;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;

public interface CustomFoodTruckRepository {

  List<FindFoodTruckDto> findFoodTrucksWithPaging(String name, Long smallRegionId,
      Long categoryId,
      String lat,
      String lng,
      PagingDto paging);


  Integer countFoodTrucks(String name, Long smallRegionId,
      Long categoryId,
      String lat,
      String lng);


  default Integer countFoodTrucks(String name) {

    return countFoodTrucks(name, null, null, null, null);
  }

  default Integer countFoodTrucks(String name, Long smallRegionId) {
    return countFoodTrucks(name, smallRegionId, null, null, null);
  }

  default Integer countFoodTrucks(String name, Long smallRegionId, Long categoryId) {
    return countFoodTrucks(name, smallRegionId, categoryId, null, null);
  }


  default Integer countHeoolInteger() {
    return 1;
  }

}
