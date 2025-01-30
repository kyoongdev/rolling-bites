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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food_truck_region", indexes = {
    @Index(name = "food_truck_region_lat_lng_region", columnList = "lat,lng")})
public class FoodTruckRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "lat")
  private String lat;

  @Column(name = "lng")
  private String lng;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "small_region_id")
  private SmallRegion smallRegion;


}
