package kyoongdev.rolling_bites.modules.foodTruck.repository;


import java.util.List;
import kyoongdev.rolling_bites.common.batch.BatchRepository;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FoodTruckCategoryBatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FoodTruckCategoryBatchRepository implements
    BatchRepository<FoodTruckCategoryBatchDto> {

  private final JdbcTemplate jdbcTemplate;


  @Override
  public void batchInsert(List<FoodTruckCategoryBatchDto> dataList) {
    jdbcTemplate.batchUpdate(
        "INSERT INTO food_truck_category (food_truck_id, category_id) VALUES (?, ?)", dataList,
        dataList.size(), (ps, data) -> {
          ps.setLong(1, data.getFoodTruckId());
          ps.setLong(2, data.getCategoryId());

        });
  }
}
