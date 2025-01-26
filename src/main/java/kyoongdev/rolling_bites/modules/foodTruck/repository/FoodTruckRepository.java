package kyoongdev.rolling_bites.modules.foodTruck.repository;


import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {

}
