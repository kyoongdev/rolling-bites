package kyoongdev.rolling_bites.modules.foodTruck.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindFoodTruckDto {

  private Long id;

  private String name;

  private Integer openAt;

  private Integer closeAt;

  private Long foodTruckRegionId;

  private Integer lat;
  private Integer lng;

  private String regionName;

  private Long smallRegionId;
  private String smallRegionName;
  private Long largeRegionId;
  private String largeRegionName;
  private List<FindFoodTruckCategoryDto> categories = new ArrayList<>();


}
