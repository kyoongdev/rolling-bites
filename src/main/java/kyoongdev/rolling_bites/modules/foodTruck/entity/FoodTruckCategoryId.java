package kyoongdev.rolling_bites.modules.foodTruck.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Embeddable
public class FoodTruckCategoryId implements Serializable {


  @Column(name = "food_truck_id")
  private Long foodTruckId;


  @Column(name = "category_id")
  private Long categoryId;
}
