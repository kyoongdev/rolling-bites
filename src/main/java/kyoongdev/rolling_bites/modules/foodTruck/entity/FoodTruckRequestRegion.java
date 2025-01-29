package kyoongdev.rolling_bites.modules.foodTruck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kyoongdev.rolling_bites.modules.region.entity.SmallRegion;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "food_truck_request_region", indexes = {
    @Index(name = "food_truck_request_region_lat_lng_region", columnList = "lat,lng")})
public class FoodTruckRequestRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "lat")
  private Integer lat;

  @Column(name = "lng")
  private Integer lng;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "smallRegoinId")
  private SmallRegion smallRegion;

}
