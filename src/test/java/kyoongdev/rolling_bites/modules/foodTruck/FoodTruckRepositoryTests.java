package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.List;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruck;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@RepositoryTest
@Import(FoodTruckDataConfig.class)
class FoodTruckRepositoryTests {

  @Autowired
  FoodTruckRepository foodTruckRepository;


  @Test
  @DisplayName("푸드 트럭 조회 테스트")
  void findFoodTrucks() {

    List<FoodTruck> foodTrucks = foodTruckRepository.findAll();

    System.out.println(foodTrucks.size());

  }
}
