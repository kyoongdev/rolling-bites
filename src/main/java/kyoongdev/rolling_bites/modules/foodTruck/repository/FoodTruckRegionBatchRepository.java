package kyoongdev.rolling_bites.modules.foodTruck.repository;


import java.util.List;
import kyoongdev.rolling_bites.common.batch.BatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckRegionBatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodTruckRegionBatchRepository implements BatchRepository<FoodTruckRegionBatchDto> {

  private final JdbcTemplate jdbcTemplate;


  @Override
  public void batchInsert(List<FoodTruckRegionBatchDto> dataList) {
    int[][] result = jdbcTemplate.batchUpdate(
        "INSERT INTO food_truck_region (lat, lng, name, small_region_id) VALUES (?, ?, ?, ?)",
        dataList, dataList.size(), (ps, data) -> {
          ps.setString(1, data.getLat());
          ps.setString(2, data.getLng());
          ps.setString(3, data.getName());
          ps.setLong(4, data.getSmallRegionId());
        });

    System.out.println("?RESULT : " + result);
  }


}
