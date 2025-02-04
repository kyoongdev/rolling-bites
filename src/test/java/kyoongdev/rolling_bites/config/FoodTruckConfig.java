package kyoongdev.rolling_bites.config;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import kyoongdev.rolling_bites.common.batch.MultiThreadExecutor;
import kyoongdev.rolling_bites.modules.foodTruck.FoodTruckBatch;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckBatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckCategoryBatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRegionBatchRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;


@TestConfiguration
@TestPropertySource(properties = {
    "spring.config.location = src/main/resources/application.yml"
})
public class FoodTruckConfig {


  @Value("${multi-thread.thread-count}")
  private int NUM_THREAD;


  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(NUM_THREAD);
  }


  @Bean
  public MultiThreadExecutor multiThreadExecutor(ExecutorService executorService) {
    return new MultiThreadExecutor();
  }


  @Bean
  public FoodTruckRegionBatchRepository foodTruckRegionBatchRepository(JdbcTemplate jdbcTemplate) {
    return new FoodTruckRegionBatchRepository(jdbcTemplate);
  }

  @Bean
  public FoodTruckBatchRepository foodTruckBatchRepository(JdbcTemplate jdbcTemplate) {
    return new FoodTruckBatchRepository(jdbcTemplate);
  }

  @Bean
  public FoodTruckCategoryBatchRepository foodTruckCategoryBatchRepository(
      JdbcTemplate jdbcTemplate) {
    return new FoodTruckCategoryBatchRepository(jdbcTemplate);
  }


  @Bean
  public FoodTruckBatch foodTruckBatch(JdbcTemplate jdbcTemplate,
      MultiThreadExecutor multiThreadExecutor,
      FoodTruckRegionBatchRepository foodTruckRegionBatchRepository,
      FoodTruckBatchRepository foodTruckBatchRepository,
      FoodTruckCategoryBatchRepository foodTruckCategoryBatchRepository) {
    return new FoodTruckBatch(jdbcTemplate, multiThreadExecutor, foodTruckRegionBatchRepository,
        foodTruckBatchRepository, foodTruckCategoryBatchRepository);
  }


}
