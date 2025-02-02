package kyoongdev.rolling_bites.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class BatchConfig {

  @Value("${multi-thread.thread-count}")
  private int threadCount;

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(threadCount);
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
