package kyoongdev.rolling_bites.modules.foodTruck.repository;

import java.util.List;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;

public interface CustomFoodTruckRepository {

  List<FindFoodTruckDto> findFoodTrucksWithPaging(String name, Long smallRegionId,
      Long categoryId,
      Integer lat,
      Integer lng,
      PagingDto paging);

  Integer countFoodTrucks(String name, Long smallRegionId,
      Long categoryId,
      Integer lat,
      Integer lng);

}
