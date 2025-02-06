package kyoongdev.rolling_bites.modules.category.repository;

import kyoongdev.rolling_bites.modules.category.entity.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRequestRepository extends JpaRepository<CategoryRequest, Long> {

}
