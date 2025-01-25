package kyoongdev.rolling_bites.modules.foodTruck.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kyoongdev.rolling_bites.modules.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "food_truck_request")
public class FoodTruckReport {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  @ManyToOne
  @JoinColumn(name = "food_truck_id")
  private FoodTruck foodTruck;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
