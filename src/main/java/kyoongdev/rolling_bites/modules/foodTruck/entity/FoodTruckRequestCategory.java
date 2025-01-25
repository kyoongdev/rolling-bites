package kyoongdev.rolling_bites.modules.foodTruck.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import kyoongdev.rolling_bites.modules.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "food_truck_request_category")
@Entity
public class FoodTruckRequestCategory {

  @EmbeddedId
  private FoodTruckRequestCategoryId id;

  @MapsId("foodTruckRequestId")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "food_truck_request_id")
  private FoodTruckRequest foodTruckRequest;

  @MapsId("categoryId")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id")
  private Category category;


}
