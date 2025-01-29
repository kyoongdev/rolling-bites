package kyoongdev.rolling_bites.modules.foodTruck.repository;


import ch.qos.logback.core.util.StringUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kyoongdev.rolling_bites.common.paging.PagingDto;
import kyoongdev.rolling_bites.common.queryDsl.CustomQueryDsl;
import kyoongdev.rolling_bites.modules.category.entity.QCategory;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckCategoryDto;
import kyoongdev.rolling_bites.modules.foodTruck.dto.FindFoodTruckDto;
import kyoongdev.rolling_bites.modules.foodTruck.entity.QFoodTruck;
import kyoongdev.rolling_bites.modules.foodTruck.entity.QFoodTruckCategory;
import kyoongdev.rolling_bites.modules.foodTruck.entity.QFoodTruckRegion;
import kyoongdev.rolling_bites.modules.region.entity.QLargeRegion;
import kyoongdev.rolling_bites.modules.region.entity.QSmallRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomFoodTruckRepositoryImpl extends CustomQueryDsl implements
    CustomFoodTruckRepository {

  @Autowired
  JPAQueryFactory jpaQueryFactory;

  QFoodTruck foodTruck = QFoodTruck.foodTruck;
  QFoodTruckCategory foodTruckCategory = QFoodTruckCategory.foodTruckCategory;
  QCategory category = QCategory.category;
  QFoodTruckRegion foodTruckRegion = QFoodTruckRegion.foodTruckRegion;
  QSmallRegion smallRegion = QSmallRegion.smallRegion;
  QLargeRegion largeRegion = QLargeRegion.largeRegion;

  @Override
  public List<FindFoodTruckDto> findFoodTrucksWithPaging(String name, Long smallRegionId,
      Long categoryId, Integer lat, Integer lng, PagingDto paging) {

    return jpaQueryFactory
        .select(
            Projections.constructor(FindFoodTruckDto.class,
                foodTruck.id.as("id"),
                foodTruck.name.as("name"),
                foodTruck.openAt.as("openAt"),
                foodTruck.closeAt.as("closeAt"),
                foodTruckRegion.id.as("foodTruckRegionId"),
                foodTruckRegion.lat.as("lat"),
                foodTruckRegion.lng.as("lng"),
                foodTruckRegion.name.as("regionName"),
                smallRegion.id.as("smallRegionId"),
                smallRegion.name.as("smallRegionName"),
                largeRegion.id.as("largeRegionId"),
                largeRegion.name.as("largeRegionName"),
                GroupBy.list(
                    Projections.constructor(FindFoodTruckCategoryDto.class,
                        category.id.as("id"),
                        category.name.as("name")
                    )
                )
            )
        )
        .from(foodTruck)
        .innerJoin(foodTruckRegion)
        .innerJoin(smallRegion).on(foodTruckRegion.smallRegion.id.eq(smallRegion.id))
        .innerJoin(largeRegion).on(smallRegion.largeRegion.id.eq(largeRegion.id))
        .innerJoin(foodTruckCategory)
        .innerJoin(category).on(foodTruckCategory.category.id.eq(category.id))
        .where(
            filterWhereClause(eqFoodTruckName(name), eqCategoryId(categoryId), eqLatLng(lat, lng),
                eqSmallRegionId(smallRegionId)))
        .offset(paging.getOffset())
        .limit(paging.getLimit())
        .groupBy(foodTruck.id)
        .fetch();
  }


  @Override
  public Integer countFoodTrucks(String name, Long smallRegionId,
      Long categoryId, Integer lat,
      Integer lng) {
    return Objects.requireNonNull(jpaQueryFactory
        .select(
            foodTruck.count()
        )
        .from(foodTruck)
        .innerJoin(foodTruckRegion)
        .innerJoin(smallRegion).on(foodTruckRegion.smallRegion.id.eq(smallRegion.id))
        .innerJoin(largeRegion).on(smallRegion.largeRegion.id.eq(largeRegion.id))
        .innerJoin(foodTruckCategory)
        .innerJoin(category).on(foodTruckCategory.category.id.eq(category.id))
        .where(eqFoodTruckName(name), eqCategoryId(categoryId), eqLatLng(lat, lng),
            eqSmallRegionId(smallRegionId))
        .groupBy(foodTruck.id)
        .fetchOne()).intValue();
  }

  private BooleanExpression eqFoodTruckName(String name) {
    if (StringUtil.isNullOrEmpty(name)) {
      return null;
    }

    return foodTruck.name.eq(name);
  }


  private BooleanExpression eqCategoryId(Long categoryId) {

    if (Optional.ofNullable(categoryId).isEmpty()) {
      return null;
    }

    return category.id.eq(categoryId);
  }

  private BooleanExpression eqLatLng(Integer lat, Integer lng) {
    if (Optional.ofNullable(lat).isEmpty() || Optional.ofNullable(lng).isEmpty()) {
      return null;
    }

    return foodTruckRegion.lat.eq(lat).and(foodTruckRegion.lng.eq(lng));
  }


  private BooleanExpression eqSmallRegionId(Long smallRegionId) {
    if (Optional.ofNullable(smallRegionId).isEmpty()) {
      return null;
    }

    return smallRegion.id.eq(smallRegionId);
  }
}
