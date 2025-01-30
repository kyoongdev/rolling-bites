package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.List;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.config.FoodTruckConfig;
import kyoongdev.rolling_bites.modules.category.repository.CategoryRepository;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruck;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckCategoryRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRegionRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRepository;
import kyoongdev.rolling_bites.modules.region.entity.LargeRegion;
import kyoongdev.rolling_bites.modules.region.repository.LargeRegionRepository;
import kyoongdev.rolling_bites.modules.region.repository.SmallRegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.StopWatch;


@RepositoryTest
@SpringJUnitConfig(FoodTruckConfig.class)
class FoodTruckRepositoryTests {

  @Autowired
  FoodTruckBatch foodTruckBatch;

  @Autowired
  FoodTruckRepository foodTruckRepository;

  @Autowired
  FoodTruckRegionRepository foodTruckRegionRepository;

  @Autowired
  LargeRegionRepository largeRegionRepository;

  @Autowired
  SmallRegionRepository smallRegionRepository;
  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  FoodTruckCategoryRepository foodTruckCategoryRepository;


  @Test
  @DisplayName("푸드 트럭 조회 테스트")
//  @SqlGroup({
//      @Sql(value = "classpath:sql/create-food-truck.sql",
//          executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),

//  })
  @Sql(value = "classpath:sql/delete-food-truck.sql",
      executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void findFoodTrucks() {
    foodTruckBatch.setup();
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    List<FoodTruck> foodTrucks = foodTruckRepository.findAll();
    List<LargeRegion> largeRegions = largeRegionRepository.findAll();

    System.out.println("findFoodTrucks 실행 시간: " + stopWatch.getTotalTimeNanos() + " ns");

    System.out.println("푸드 트럭 개수: " + foodTrucks.size());
    System.out.println("리전 개수: " + largeRegions.size());
  }


}
