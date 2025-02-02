package kyoongdev.rolling_bites.modules.category.repository;


import java.util.Optional;
import kyoongdev.rolling_bites.modules.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findCategoryByName(String name);

}
