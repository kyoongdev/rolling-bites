package kyoongdev.rolling_bites.modules.foodTruck.repository;

import java.util.List;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.repository.FindFoodTrucksWhere;

public interface CustomFoodTruckRepository {

  List<FindFoodTruckDto> findFoodTrucksWithPaging(FindFoodTrucksWhere where,
      PagingDto paging);


  Integer countFoodTrucks(FindFoodTrucksWhere where);


}
