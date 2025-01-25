package kyoongdev.rolling_bites.modules.foodTruck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kyoongdev.rolling_bites.modules.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "food_truck_visit")
@Getter
@NoArgsConstructor
public class FoodTruckVisit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "craeted_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "food_truck_id")
  private FoodTruck foodTruck;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
