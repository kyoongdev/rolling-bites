package kyoongdev.rolling_bites.common.batch;

import java.util.List;

public interface BatchRepository<T> {

  void batchInsert(List<T> data);
}
