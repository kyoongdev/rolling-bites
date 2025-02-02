package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.ArrayList;
import java.util.List;
import kyoongdev.rolling_bites.common.batch.MultiThreadExecutor;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckBatchDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckCategoryBatchDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckRegionBatchDto;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckBatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckCategoryBatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRegionBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class FoodTruckBatch {


  private final JdbcTemplate jdbcTemplate;

  private final MultiThreadExecutor multiThreadExecutor;
  private final FoodTruckRegionBatchRepository foodTruckRegionBatchRepository;
  private final FoodTruckBatchRepository foodTruckBatchRepository;
  private final FoodTruckCategoryBatchRepository foodTruckCategoryBatchRepository;


  private final int TOTAL_COUNT = 100_000; // 10만 개 생성


  void setup() {
    try {
      System.out.println("🔹 Setting up test database...");

      insertRegion();
      insertCategory();
      insertFoodTruckRegions();
      insertFoodTrucks();
      insertFoodTruckCategories();

    } catch (Exception e) {
      System.err.println("SETUP ERROR: " + e.getMessage());
      throw new Error(e);
    }
  }

  private void insertRegion() {
    jdbcTemplate.execute("START TRANSACTION ");
    jdbcTemplate.update(
        "INSERT INTO large_region (name) VALUES ('test_large_region_for_food_truck')");
    jdbcTemplate.update("INSERT INTO small_region (name, large_region_id) " +
        "SELECT CONCAT('foot_truck_small_region_', id), id FROM large_region");

    jdbcTemplate.execute("COMMIT");
  }

  private void insertCategory() {
    jdbcTemplate.execute("START TRANSACTION ");
    jdbcTemplate.update("INSERT INTO category (name) VALUES ('순대')");
    jdbcTemplate.update("INSERT INTO category (name) VALUES ('떡볶이')");
    jdbcTemplate.execute("COMMIT");
  }


  private void insertFoodTruckRegions() throws Exception {
    System.out.println("🔹 Inserting 100,000 Food Truck Regions");

    List<Long> smallRegionIds = jdbcTemplate.queryForList("SELECT id FROM small_region",
        Long.class);

    // 10만 개의 데이터 생성
    List<FoodTruckRegionBatchDto> dataList = new ArrayList<>();
    for (int i = 0; i < TOTAL_COUNT; i++) {
      dataList.add(FoodTruckRegionBatchDto.builder().lat(String.valueOf(36.00001 + i * 0.00001))
          .lng(String.valueOf(40 + i * 0.00001)).name("서울 특별시 어쩌구 저쩌구")
          .smallRegionId(smallRegionIds.get(i % smallRegionIds.size())).build());
    }

    multiThreadExecutor.executeBatch(dataList, TOTAL_COUNT,
        foodTruckRegionBatchRepository);

    jdbcTemplate.execute("COMMIT");
  }

  private void insertFoodTrucks() throws Exception {

    List<Long> regionIds = jdbcTemplate.queryForList("SELECT id FROM food_truck_region",
        Long.class);

    List<FoodTruckBatchDto> dataList = new ArrayList<>();
    for (int i = 0; i < TOTAL_COUNT; i++) {
      dataList.add(FoodTruckBatchDto.builder().name("FoodTruck_" + i).openAt(10).closeAt(22)
          .regionId(regionIds.get(i)).build());
    }

    multiThreadExecutor.executeBatch(dataList, TOTAL_COUNT, foodTruckBatchRepository);

    jdbcTemplate.execute("COMMIT");

  }

  private void insertFoodTruckCategories() throws Exception {
    List<Long> foodTruckIds = jdbcTemplate.queryForList(
        "SELECT id FROM food_truck", Long.class);
    List<Long> categoryIds = jdbcTemplate.queryForList("SELECT id FROM category", Long.class);

    List<FoodTruckCategoryBatchDto> dataList = new ArrayList<>();

    for (int i = 0; i < TOTAL_COUNT; i++) {
      dataList.add(FoodTruckCategoryBatchDto.builder().foodTruckId(foodTruckIds.get(i))
          .categoryId(categoryIds.get(i % categoryIds.size())).build());
    }

    multiThreadExecutor.executeBatch(dataList, TOTAL_COUNT, foodTruckCategoryBatchRepository);

    jdbcTemplate.execute("COMMIT");
  }


}