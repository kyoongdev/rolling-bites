package kyoongdev.rolling_bites.modules.foodTruck.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindFoodTruckDto {

  private Long id;

  private String name;

  private Integer openAt;

  private Integer closeAt;

  private Long foodTruckRegionId;

  private String lat;
  private String lng;

  private String regionName;

  private Long smallRegionId;
  private String smallRegionName;
  private Long largeRegionId;
  private String largeRegionName;
  private List<FindFoodTruckCategoryDto> categories = new ArrayList<>();

  public FindFoodTruckDto(Long id, String name, Integer openAt, Integer closeAt,
      Long foodTruckRegionId, String lat, String lng, String regionName, Long smallRegionId,
      String smallRegionName, Long largeRegionId, String largeRegionName,
      List<FindFoodTruckCategoryDto> categories
  ) {

    this.id = id;
    this.name = name;
    this.openAt = openAt;
    this.closeAt = closeAt;
    this.foodTruckRegionId = foodTruckRegionId;
    this.lat = lat;
    this.lng = lng;
    this.regionName = regionName;
    this.smallRegionId = smallRegionId;
    this.smallRegionName = smallRegionName;
    this.largeRegionId = largeRegionId;
    this.largeRegionName = largeRegionName;
    this.categories = categories;

  }

  public FindFoodTruckDto(Long id, String name, Integer openAt, Integer closeAt,
      Long foodTruckRegionId, String lat, String lng, String regionName, Long smallRegionId,
      String smallRegionName, Long largeRegionId, String largeRegionName

  ) {

    this.id = id;
    this.name = name;
    this.openAt = openAt;
    this.closeAt = closeAt;
    this.foodTruckRegionId = foodTruckRegionId;
    this.lat = lat;
    this.lng = lng;
    this.regionName = regionName;
    this.smallRegionId = smallRegionId;
    this.smallRegionName = smallRegionName;
    this.largeRegionId = largeRegionId;
    this.largeRegionName = largeRegionName;


  }


}
