package kyoongdev.rolling_bites.modules.category;


import java.util.Optional;
import kyoongdev.rolling_bites.common.annotation.RepositoryTest;
import kyoongdev.rolling_bites.modules.category.entity.Category;
import kyoongdev.rolling_bites.modules.category.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class CategoryRepositoryTest {

  @Autowired
  CategoryRepository categoryRepository;


  @Test
  @DisplayName("카테고리 생성 및 조회")
  void createCategory() {
    Category newCategory = Category.builder().name("분식").build();

    categoryRepository.save(newCategory);

    Optional<Category> category = categoryRepository.findCategoryByName("분식");

    Assertions.assertTrue(category.isPresent());

    Category realCategory = category.get();

    Assertions.assertEquals(realCategory.getName(), "분식");
  }
}
