package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.List;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.config.FoodTruckConfig;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.StopWatch;


@RepositoryTest
@SpringJUnitConfig(FoodTruckConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlGroup({
    @Sql(value = "classpath:sql/delete-food-truck.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_CLASS),
    @Sql(value = "classpath:sql/delete-food-truck.sql",
        executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
})
class FoodTruckRepositoryTests {

  @Autowired
  FoodTruckBatch foodTruckBatch;

  @Autowired
  FoodTruckRepository foodTruckRepository;


  @BeforeAll
  void saveFoodTruck() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    foodTruckBatch.saveAll();
    stopWatch.stop();

    System.out.println("데이터 생성 시간 : " + stopWatch.getTotalTimeMillis() + "ms");
  }


  @Test
  @DisplayName("푸드 트럭 조회 테스트")
  void findFoodTrucks() {

    List<FindFoodTruckDto> foodTrucks = foodTruckRepository.findFoodTrucksWithPaging(
        "FoodTruck_10000",
        null, null, null, null,
        PagingDto.builder().page(1).limit(500)
            .build());

//    Assertions.assertEquals(20, foodTrucks.size());

  }

  @Test
  @DisplayName("푸드 트럭 조회 테스트 - 쿼리 분리")
  void findFoodTrucksWithoutGroupBy() {

    List<FindFoodTruckDto> foodTrucks = foodTruckRepository.findFoodTrucksWithPagingWithoutGroupBy(
        "FoodTruck_10000",
        null, null, null, null,
        PagingDto.builder().page(1).limit(500)
            .build());

//    Assertions.assertEquals(20, foodTrucks.size());

  }

  @Test
  @DisplayName("푸드 트럭 개수 조회 테스트")
  void countFoodTrucks() {
    Integer count = foodTruckRepository.countFoodTrucks("FoodTruck_1", null, null, null, null);


  }


}
