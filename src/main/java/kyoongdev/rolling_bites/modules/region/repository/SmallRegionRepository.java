package kyoongdev.rolling_bites.modules.region.repository;

import java.util.Optional;
import kyoongdev.rolling_bites.modules.region.entity.SmallRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallRegionRepository extends JpaRepository<SmallRegion, Long> {

  Optional<SmallRegion> findSmallRegionByName(String name);


}
