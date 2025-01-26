package kyoongdev.rolling_bites.modules.region.repository;

import java.util.Optional;
import kyoongdev.rolling_bites.modules.region.entity.LargeRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LargeRegionRepository extends JpaRepository<LargeRegion, Long> {

  Optional<LargeRegion> findLargeRegionByName(String name);

}
