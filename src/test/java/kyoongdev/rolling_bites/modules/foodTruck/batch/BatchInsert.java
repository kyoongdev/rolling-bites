package kyoongdev.rolling_bites.modules.foodTruck.batch;

import java.util.List;

public interface BatchInsert<T> {

  List<T> getData();

  void executeBatch(List<T> subDataList);

}
