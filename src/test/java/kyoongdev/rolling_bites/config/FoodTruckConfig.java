package kyoongdev.rolling_bites.config;


import javax.sql.DataSource;
import kyoongdev.rolling_bites.modules.foodTruck.FoodTruckBatch;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class FoodTruckConfig {


  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }


  @Bean
  public FoodTruckBatch foodTruckBatch(JdbcTemplate jdbcTemplate, DataSource dataSource) {
    return new FoodTruckBatch(jdbcTemplate, dataSource);
  }
}
