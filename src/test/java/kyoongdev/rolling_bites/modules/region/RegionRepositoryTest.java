package kyoongdev.rolling_bites.modules.region;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kyoongdev.rolling_bites.modules.region.entity.LargeRegion;
import kyoongdev.rolling_bites.modules.region.entity.SmallRegion;
import kyoongdev.rolling_bites.modules.region.repository.LargeRegionRepository;
import kyoongdev.rolling_bites.modules.region.repository.SmallRegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionRepositoryTest {

  @Autowired
  LargeRegionRepository largeRegionRepository;

  @Autowired
  SmallRegionRepository smallRegionRepository;


  @Test
  @DisplayName("대지역 생성 및 조회")
  void createLargeRegion() {
    List<SmallRegion> smallRegions = new ArrayList<>();

    smallRegions.add(SmallRegion.builder().name("광진구").build());
    smallRegions.add(SmallRegion.builder().name("강남구").build());
    LargeRegion newLargeRegion = LargeRegion.builder().name("서울").smallRegions(smallRegions)
        .build();

    largeRegionRepository.save(newLargeRegion);

    Optional<LargeRegion> largeRegion = largeRegionRepository.findLargeRegionByName("서울");

    Assertions.assertTrue(largeRegion.isPresent());

    LargeRegion realLargeRegion = largeRegion.get();

    Assertions.assertEquals(realLargeRegion.getName(), "서울");
    List<SmallRegion> realSmallRegions = realLargeRegion.getSmallRegions();

    Assertions.assertEquals(realSmallRegions.size(), 2);

    SmallRegion gwangjin = realSmallRegions.stream()
        .filter(region -> region.getName().equals("광진구"))
        .findFirst().orElse(null);

    Assertions.assertNotNull(gwangjin);
    Assertions.assertEquals(gwangjin.getName(), "광진구");

    SmallRegion gangnam = realSmallRegions.stream().filter(region -> region.getName().equals("강남구"))
        .findFirst().orElse(null);

    Assertions.assertNotNull(gangnam);
    Assertions.assertEquals(gangnam.getName(), "강남구");

    SmallRegion songpa = realSmallRegions.stream().filter(region -> region.getName().equals("송파구"))
        .findFirst().orElse(null);

    Assertions.assertNull(songpa);


  }


}