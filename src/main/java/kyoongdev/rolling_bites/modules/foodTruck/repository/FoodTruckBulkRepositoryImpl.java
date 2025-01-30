package kyoongdev.rolling_bites.modules.foodTruck.repository;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruck;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodTruckBulkRepositoryImpl {

  private final JdbcTemplate jdbcTemplate;

  public void bulkInsertFoodTruck(List<FoodTruck> foodTrucks) {
    String sql = "INSERT INTO food_truck (name, open_at, close_at, region_id)"
        +
        "VALUES (?, ?, ?, ?)";

    jdbcTemplate.batchUpdate(sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            FoodTruck foodTruck = foodTrucks.get(i);
            ps.setString(1, foodTruck.getName());
            ps.setInt(2, foodTruck.getOpenAt());
            ps.setInt(3, foodTruck.getCloseAt());
            ps.setInt(4, foodTruck.getRegion().getId().intValue());
          }

          @Override
          public int getBatchSize() {
            return foodTrucks.size();
          }
        });
  }
}
