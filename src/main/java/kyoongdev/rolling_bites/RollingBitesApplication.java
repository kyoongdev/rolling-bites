package kyoongdev.rolling_bites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RollingBitesApplication {


  public static void main(String[] args) {
    SpringApplication.run(RollingBitesApplication.class, args);
  }


}
