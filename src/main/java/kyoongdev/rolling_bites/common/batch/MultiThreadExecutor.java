package kyoongdev.rolling_bites.common.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultiThreadExecutor {


  @Value("${multi-thread.thread-count}")
  private int NUM_THREAD;


  public <T> void executeBatch(List<T> dataList, int totalCount, BatchRepository<T> batchRepository)
      throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREAD);
    List<Future<?>> futures = new ArrayList<>();
    int batchSize = (int) Math.ceil((double) totalCount / NUM_THREAD); // 올바른 배치 크기 계산

    for (int i = 0; i < NUM_THREAD; i++) {
      int start = i * batchSize;
      int end = Math.min(start + batchSize, totalCount); // 데이터 손실 방지

      if (start >= end) {
        break;
      }

      List<T> subList = new ArrayList<>(dataList.subList(start, end)); // subList 안전하게 복사

      futures.add(executorService.submit(() -> {
        try {
          batchRepository.batchInsert(subList);
        } catch (Exception e) {
          System.err.println("Batch Execution Error : " + e.getMessage());
          throw new RuntimeException(e);
        }
      }));
    }

    for (Future<?> future : futures) {
      future.get();
    }

    executorService.shutdown();
  }
}