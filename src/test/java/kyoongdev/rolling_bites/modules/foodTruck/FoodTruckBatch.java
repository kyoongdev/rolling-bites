package kyoongdev.rolling_bites.modules.foodTruck;

import java.util.ArrayList;
import java.util.List;
import kyoongdev.rolling_bites.common.batch.MultiThreadExecutor;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckRegionBatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodTruckBatch {


  private final JdbcTemplate jdbcTemplate;


  private final MultiThreadExecutor multiThreadExecutor;


  private final int TOTAL_COUNT = 10000; // 10만 개 생성


  void setup() {
    try {
      System.out.println("🔹 Setting up test database...");

//      insertRegion();
//      insertCategory();
//      insertFoodTruckRegions();
//      insertFoodTruck();
//      insertFoodTruckCategory();

    } catch (Exception e) {
      System.err.println("SETUP ERROR: " + e.getMessage());
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

//    multiThreadExecutor.executeBatch(dataList, TOTAL_COUNT, BATCH_SIZE, () -> {
//      batchInsertFoodTruckRegions(dataList);
//      return null;
//    });

    jdbcTemplate.execute("COMMIT");
  }

//  private void insertFoodTruck() {
//    System.out.println("🔹 Inserting Food Trucks");
//    List<Long> regionIds = jdbcTemplate.queryForList("SELECT id FROM food_truck_region",
//        Long.class);
//
//    if (regionIds.isEmpty()) {
//      System.out.println("⚠️ No data found in food_truck_region.");
//      return;
//    }
//
//    HikariDataSource hikariDataSource = (HikariDataSource) jdbcDataSource;
//    ExecutorService executorService = Executors.newFixedThreadPool(
//        hikariDataSource.getMaximumPoolSize());
//    List<List<Long>> regionSubList = splitList(regionIds);
//
//    List<Callable<Void>> tasks = regionSubList.stream()
//        .map(subList -> (Callable<Void>) () -> {
//          batchUpdateFoodTruck(subList);
//          return null;
//        }).collect(Collectors.toList());
//
//    executeBatchTasks(executorService, tasks);
//  }

//
//  private void insertFoodTruckCategory() {
//    System.out.println("🔹 Inserting Food Truck Categories");
//    List<Long> foodTruckIds = jdbcTemplate.queryForList(
//        "SELECT id FROM food_truck ORDER BY id DESC LIMIT ?", Long.class, batchSize);
//    List<Long> categoryIds = jdbcTemplate.queryForList("SELECT id FROM category", Long.class);
//
//    if (foodTruckIds.isEmpty() || categoryIds.isEmpty()) {
//      System.out.println("⚠️ No data found for food_truck or category.");
//      return;
//    }
//
//    HikariDataSource hikariDataSource = (HikariDataSource) jdbcDataSource;
//    ExecutorService executorService = Executors.newFixedThreadPool(
//        hikariDataSource.getMaximumPoolSize());
//    List<List<Long>> foodTruckSubList = splitList(foodTruckIds);
//
//    List<Callable<Void>> tasks = foodTruckSubList.stream()
//        .map(subList -> (Callable<Void>) () -> {
//          batchUpdateFoodTruckCategory(subList, categoryIds);
//          return null;
//        }).collect(Collectors.toList());
//
//    executeBatchTasks(executorService, tasks);
//  }

//  private void batchUpdateFoodTruck(List<Long> regionIds) {
//    jdbcTemplate.batchUpdate(
//        "INSERT INTO food_truck (name, open_at, close_at, region_id) VALUES (?, ?, ?, ?)",
//        new BatchPreparedStatementSetter() {
//          @Override
//          public void setValues(PreparedStatement ps, int i) throws SQLException {
//            ps.setString(1, "FoodTruck" + regionIds.get(i));
//            ps.setInt(2, 10);
//            ps.setInt(3, 22);
//            ps.setLong(4, regionIds.get(i));
//          }
//
//          @Override
//          public int getBatchSize() {
//            return regionIds.size();
//          }
//        });
//  }

//  private void batchUpdateFoodTruckCategory(List<Long> foodTruckIds, List<Long> categoryIds) {
//    jdbcTemplate.batchUpdate(
//        "INSERT INTO food_truck_category (food_truck_id, category_id) VALUES (?, ?)",
//        new BatchPreparedStatementSetter() {
//          @Override
//          public void setValues(PreparedStatement ps, int i) throws SQLException {
//            ps.setLong(1, foodTruckIds.get(i));
//            ps.setLong(2, categoryIds.get((int) (Math.random() * categoryIds.size())));
//          }
//
//          @Override
//          public int getBatchSize() {
//            return foodTruckIds.size();
//          }
//        });
//  }


}