package kyoongdev.rolling_bites.common.batch;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MultiThreadExecutor {


  private final ExecutorService executorService;


  @Value("${multi-thread.thread-count}")
  private int NUM_THREAD;


  public <T> void executeBatch(List<T> dataList, int totalCount, int batchSize,
      BatchRepository<T> batchRepository)
      throws ExecutionException, InterruptedException {

    List<Future<?>> futures = new ArrayList<>();

    for (int i = 0; i < NUM_THREAD; i++) {
      int start = i * batchSize;
      int end = (i == NUM_THREAD - 1) ? totalCount : (i + 1) * batchSize;

      List<T> subList = dataList.subList(start, end);

      futures.add(executorService.submit(() -> {
        try {
          batchRepository.batchInsert(subList);
        } catch (Exception e) {
          System.out.println("Batch Execution Error : " + e.getMessage());
          throw new Error(e);
        }
      }));
    }

    for (Future<?> future : futures) {
      future.get();

    }
    executorService.shutdown();

  }

}
