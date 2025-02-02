package kyoongdev.rolling_bites.modules.foodTruck.repository;


import java.util.List;
import kyoongdev.rolling_bites.common.batch.BatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckBatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodTruckBatchRepository implements BatchRepository<FoodTruckBatchDto> {

  private final JdbcTemplate jdbcTemplate;


  @Override
  public void batchInsert(List<FoodTruckBatchDto> dataList) {

    jdbcTemplate.batchUpdate(
        "INSERT INTO food_truck (name, open_at, close_at, region_id) VALUES (?, ?, ?, ?)", dataList,
        dataList.size(), (ps, data) -> {
          ps.setString(1, data.getName());
          ps.setInt(2, data.getOpenAt());
          ps.setInt(3, data.getCloseAt());
          ps.setLong(4, data.getRegionId());
        });

  }


}
