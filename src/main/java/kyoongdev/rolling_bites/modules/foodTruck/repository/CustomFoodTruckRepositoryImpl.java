package kyoongdev.rolling_bites.modules.foodTruck.repository;


import static com.querydsl.core.group.GroupBy.list;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomFoodTruckRepositoryImpl extends CustomQueryDsl implements
    CustomFoodTruckRepository {


  private final JPAQueryFactory jpaQueryFactory;

  private final int TRANFORM_MINIMUN_LIMIT = 500;

  QFoodTruck foodTruck = QFoodTruck.foodTruck;
  QFoodTruckCategory foodTruckCategory = QFoodTruckCategory.foodTruckCategory;
  QCategory category = QCategory.category;
  QFoodTruckRegion foodTruckRegion = QFoodTruckRegion.foodTruckRegion;
  QSmallRegion smallRegion = QSmallRegion.smallRegion;
  QLargeRegion largeRegion = QLargeRegion.largeRegion;

  @Override
  public List<FindFoodTruckDto> findFoodTrucksWithPaging(String name, Long smallRegionId,
      Long categoryId, String lat, String lng, PagingDto paging) {

    BooleanExpression[] whereClauses = filterWhereClause(startsWithFoodTruckName(name),
        eqSmallRegionId(smallRegionId), eqCategoryId(categoryId), eqLatLng(lat, lng));

    Integer limit = paging.getLimit();

    return limit > TRANFORM_MINIMUN_LIMIT ? findFoodTrucksWithPagingByTransform(whereClauses,
        paging) : findFoodTrucksWithPagingWithoutGroupBy(whereClauses, paging);
  }


  @Override
  public Integer countFoodTrucks(String name, Long smallRegionId,
      Long categoryId, String lat,
      String lng) {
    return Objects.requireNonNull(jpaQueryFactory
        .select(
            foodTruck.count()
        )
        .from(foodTruck)
        .innerJoin(foodTruckRegion).on(foodTruckRegion.id.eq(foodTruck.region.id))
        .innerJoin(smallRegion).on(foodTruckRegion.smallRegion.id.eq(smallRegion.id))
        .innerJoin(largeRegion).on(smallRegion.largeRegion.id.eq(largeRegion.id))
        .innerJoin(foodTruckCategory)
        .on(foodTruckCategory.foodTruck.eq(foodTruck))
        .innerJoin(foodTruckCategory.category, category)
        .where(eqFoodTruckName(name), eqCategoryId(categoryId), eqLatLng(lat, lng),
            eqSmallRegionId(smallRegionId))
        .groupBy(foodTruck.id)
        .fetchOne()).intValue();
  }


  private List<FindFoodTruckDto> findFoodTrucksWithPagingByTransform(
      BooleanExpression[] whereClauses, PagingDto paging) {
    return jpaQueryFactory
        .from(foodTruck)
        .innerJoin(foodTruckRegion).on(foodTruckRegion.id.eq(foodTruck.region.id))
        .innerJoin(smallRegion).on(foodTruckRegion.smallRegion.id.eq(smallRegion.id))
        .innerJoin(largeRegion).on(smallRegion.largeRegion.id.eq(largeRegion.id))
        .innerJoin(foodTruckCategory)
        .on(foodTruckCategory.foodTruck.eq(foodTruck))
        .innerJoin(foodTruckCategory.category, category)
        .where(whereClauses)
        .offset(paging.getOffset())
        .limit(paging.getLimit())
        .transform(
            GroupBy.groupBy(foodTruck.id).list(Projections.constructor(FindFoodTruckDto.class,
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
                list(
                    Projections.constructor(FindFoodTruckCategoryDto.class,
                        category.id.as("categoryId"),
                        category.name.as("categoryName")
                    )

                )
            )));
  }

  private List<FindFoodTruckDto> findFoodTrucksWithPagingWithoutGroupBy(
      BooleanExpression[] whereClauses, PagingDto paging) {
    List<FindFoodTruckDto> foodTrucks = jpaQueryFactory
        .select(Projections.constructor(FindFoodTruckDto.class,
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
            largeRegion.name.as("largeRegionName")))
        .from(foodTruck)
        .innerJoin(foodTruckRegion).on(foodTruckRegion.id.eq(foodTruck.region.id))
        .innerJoin(smallRegion).on(foodTruckRegion.smallRegion.id.eq(smallRegion.id))
        .innerJoin(largeRegion).on(smallRegion.largeRegion.id.eq(largeRegion.id))
        .where(whereClauses)
        .offset(paging.getOffset())
        .limit(paging.getLimit())
        .fetch();

    for (FindFoodTruckDto foodTruckDto : foodTrucks) {
      List<FindFoodTruckCategoryDto> categories = jpaQueryFactory.select(Projections.constructor(
              FindFoodTruckCategoryDto.class,
              category.id,
              category.name
          )).from(foodTruckCategory)
          .innerJoin(category).on(category.eq(foodTruckCategory.category))
          .where(foodTruckCategory.foodTruck.id.eq(foodTruckDto.getId())).fetch();

      foodTruckDto.setCategories(categories);
    }

    return foodTrucks;

  }


  private BooleanExpression eqFoodTruckName(String name) {
    if (StringUtil.isNullOrEmpty(name)) {
      return null;
    }

    return foodTruck.name.eq(name);
  }

  private BooleanExpression startsWithFoodTruckName(String name) {
    if (StringUtil.isNullOrEmpty(name)) {
      return null;
    }

    return foodTruck.name.startsWith(name);
  }


  private BooleanExpression eqCategoryId(Long categoryId) {

    if (Optional.ofNullable(categoryId).isEmpty()) {
      return null;
    }

    return category.id.eq(categoryId);
  }

  private BooleanExpression eqLatLng(String lat, String lng) {
    if (StringUtil.isNullOrEmpty(lat) || StringUtil.isNullOrEmpty(lng)) {
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
