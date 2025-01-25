package kyoongdev.rolling_bites.modules.region.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor

@Entity
@Table(name = "large_region")
public class LargeRegion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @OneToMany(mappedBy = "largeRegion", fetch = FetchType.LAZY)
  private List<SmallRegion> smallRegions = new ArrayList<>();

}
