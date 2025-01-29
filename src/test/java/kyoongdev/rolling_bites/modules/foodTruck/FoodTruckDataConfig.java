package kyoongdev.rolling_bites.modules.foodTruck;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kyoongdev.rolling_bites.modules.category.entity.Category;
import kyoongdev.rolling_bites.modules.category.repository.CategoryRepository;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruck;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruckCategory;
import kyoongdev.rolling_bites.modules.foodTruck.entity.FoodTruckRegion;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckCategoryRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRegionRepository;
import kyoongdev.rolling_bites.modules.foodTruck.repository.FoodTruckRepository;
import kyoongdev.rolling_bites.modules.region.entity.LargeRegion;
import kyoongdev.rolling_bites.modules.region.entity.SmallRegion;
import kyoongdev.rolling_bites.modules.region.repository.LargeRegionRepository;
import kyoongdev.rolling_bites.modules.region.repository.SmallRegionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class FoodTruckDataConfig {

  private static final int TOTAL_COUNT = 10000; // 1만 개 데이터


  @Bean
  public CommandLineRunner loadTestData(FoodTruckRepository foodTruckRepository,
      FoodTruckRegionRepository foodTruckRegionRepository,
      LargeRegionRepository largeRegionRepository, SmallRegionRepository smallRegionRepository,
      CategoryRepository categoryRepository,
      FoodTruckCategoryRepository foodTruckCategoryRepository) {
    return args -> {
      foodTruckRegionRepository.deleteAll();
      largeRegionRepository.deleteAll();
      smallRegionRepository.deleteAll();
      LargeRegion largeRegion = LargeRegion.builder().name("서울").smallRegions(
          List.of(SmallRegion.builder().name("광진구").build(),
              SmallRegion.builder().name("송파구").build())).build();

      LargeRegion newLargeRegion = largeRegionRepository.save(largeRegion);

      List<Category> categories = List.of(Category.builder().name("순대").build(),
          Category.builder().name("떡볶이").build());
      List<Category> newCategories = categoryRepository.saveAll(categories);

      List<FoodTruck> foodTrucks = IntStream.range(1, TOTAL_COUNT + 1)
          .mapToObj(i -> {
            FoodTruckRegion region = foodTruckRegionRepository.save(
                FoodTruckRegion.builder().lat(36 + i / TOTAL_COUNT).lng(38 + i / TOTAL_COUNT)
                    .name("서울 특별시 어쩌구 저쩌구")
                    .smallRegion(newLargeRegion.getSmallRegions().get(i % 2))
                    .build());

            FoodTruck foodTruck = FoodTruck.builder()
                .name("FoodTruck" + i)
                .openAt(10)
                .closeAt(22)
                .region(region)
                .build();

            Category category = newCategories.stream().findAny().get();

            FoodTruckCategory foodTruckCategory = FoodTruckCategory.builder()
                .category(category).foodTruck(foodTruck)
                .build();

            return foodTruck;
          })
          .collect(Collectors.toList());

      foodTruckRepository.saveAll(foodTrucks);
    };
  }

}
