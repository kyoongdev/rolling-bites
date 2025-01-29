package kyoongdev.rolling_bites.modules.foodTruck.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Table(name = "food_truck_request")
@Entity
public class FoodTruckRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;


  @Column(name = "open_at")
  private Integer openAt;

  @Column(name = "close_at")
  private Integer close_at;


  @ColumnDefault("0")
  @Column(name = "like_count")
  private Integer likeCount;

  @OneToOne
  @JoinColumn(name = "foodTruckRegionId")
  private FoodTruckRegion region;

  @OneToMany(mappedBy = "foodTruckRequest", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<FoodTruckRequestCategory> categories = new ArrayList<>();
}
