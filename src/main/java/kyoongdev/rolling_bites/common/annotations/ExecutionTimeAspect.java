package kyoongdev.rolling_bites.common.annotations;


import java.lang.reflect.Method;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

  @Around("@annotation(ExecutionTime)")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();

    ExecutionTime executionTime = method.getAnnotation(ExecutionTime.class);

    String description = Optional.ofNullable(executionTime.value()).orElse(method.getName());
    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    Object result = joinPoint.proceed();
    stopWatch.stop();

    log.info(description + " : " + stopWatch.getTotalTimeMillis() + "ms");
    System.out.println(description + " : " + stopWatch.getTotalTimeMillis() + "ms");

    return result;
  }


}
