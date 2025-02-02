package kyoongdev.rolling_bites.config;


import java.util.concurrent.Executors;
import javax.sql.DataSource;
import kyoongdev.rolling_bites.common.batch.MultiThreadExecutor;
import kyoongdev.rolling_bites.modules.foodTruck.FoodTruckBatch;
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
  public MultiThreadExecutor multiThreadExecutor() {
    return new MultiThreadExecutor(Executors.newFixedThreadPool(NUM_THREAD));
  }


  @Bean
  public FoodTruckBatch foodTruckBatch(JdbcTemplate jdbcTemplate,
      MultiThreadExecutor multiThreadExecutor) {
    return new FoodTruckBatch(jdbcTemplate, multiThreadExecutor);
  }


}
