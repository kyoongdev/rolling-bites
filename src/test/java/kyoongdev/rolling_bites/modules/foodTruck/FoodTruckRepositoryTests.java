package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.List;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.config.FoodTruckConfig;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.repository.FindFoodTrucksWhere;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


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
    foodTruckBatch.saveAll();
  }


  @Test
  @DisplayName("푸드 트럭 조회 테스트")
  void findFoodTrucks() {
    FindFoodTrucksWhere where = FindFoodTrucksWhere.builder().name("FoodTruck_").build();

    List<FindFoodTruckDto> foodTrucks = foodTruckRepository.findFoodTrucksWithPaging(
        where,
        PagingDto.builder().page(1).limit(20)
            .build());

    Assertions.assertEquals(20, foodTrucks.size());

  }


  @Test
  @DisplayName("푸드 트럭 개수 조회 테스트")
  void countFoodTrucks() {
    FindFoodTrucksWhere where = FindFoodTrucksWhere.builder().name("FoodTruck_").build();
    Integer count = foodTruckRepository.countFoodTrucks(where);


  }


}
