package kyoongdev.rolling_bites.common.queryDsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Objects;
import java.util.stream.Stream;

public class CustomQueryDsl {

  protected BooleanExpression[] filterWhereClause(BooleanExpression... values) {

    return Stream.of(values).filter(Objects::nonNull)
        .toArray(BooleanExpression[]::new);

  }

}
