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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "food_truck")
@Entity
public class FoodTruck {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;


  @Column(name = "open_at")
  private Integer openAt;

  @Column(name = "close_at")
  private Integer closeAt;


  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "region_id")
  private FoodTruckRegion region;

  @OneToMany(mappedBy = "foodTruck", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Builder.Default
  private List<FoodTruckCategory> categories = new ArrayList<>();

}
