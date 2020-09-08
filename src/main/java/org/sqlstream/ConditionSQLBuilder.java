package org.sqlstream;

import org.sqlstream.condition.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

import static org.sqlstream.SQLConstants.*;

/**
 * @author WangYi
 * @since 2020/8/15
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class ConditionSQLBuilder<T, R extends Serializable, RType extends ConditionSQLBuilder<T, R, RType>>
        extends ConditionMergeBuilder implements Compare<R, RType>, Size<R, RType>, Between<R, RType>, In<R, RType>,
        Like<R, RType>, Null<RType> {

  protected final RType typedThis = (RType) this;

  @Override
  public <V> RType between(boolean condition, String fieldName, V value1, V value2) {
    if (condition) {
      this.addBetweenCondition(fieldName, value1, value2, between);
    }
    return typedThis;
  }

  @Override
  public <V> RType notBetween(boolean condition, String fieldName, V value1, V value2) {
    if (condition) {
      this.addBetweenCondition(fieldName, value1, value2, notBetween);
    }
    return typedThis;
  }

  @Override
  public <V> RType allEq(Map<R, V> params, boolean null2IsNull) {
    for (Map.Entry<R, V> entry : params.entrySet()) {
      String fieldName = LambdaUtils.getLambdaColumnName(entry.getKey());
      V value = entry.getValue();
      if (Objects.isNull(value)) {
        this.addNullCondition(fieldName, isNull);
      } else {
        this.addEqCondition(fieldName, value, eq);
      }
    }
    return typedThis;
  }

  @Override
  public <V> RType allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
    for (Map.Entry<R, V> entry : params.entrySet()) {
      R fieldName = entry.getKey();
      V value = entry.getValue();
      if (filter.test(fieldName, value)) {
        this.allEq(params, null2IsNull);
      }
    }
    return typedThis;
  }

  @Override
  public <V> RType allEq(boolean condition, Map<R, V> params, boolean null2IsNull) {
    if (condition) {
      this.allEq(params, null2IsNull);
    }
    return typedThis;
  }

  @Override
  public <V> RType allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
    if (condition) {
      this.allEq(filter, params, null2IsNull);
    }
    return typedThis;
  }

  @Override
  public <V> RType ne(boolean condition, String fieldName, V value) {
    if (condition) {
      this.ne(fieldName, value);
    }
    return typedThis;
  }

  @Override
  public <V> RType ne(String fieldName, V value) {
    this.ne(true, fieldName, value);
    return typedThis;
  }

  @Override
  public <V> RType eq(String fieldName, V value) {
    this.addEqCondition(fieldName, value, eq);
    return typedThis;
  }

  @Override
  public <V> RType eq(boolean condition, String fieldName, V value) {
    if (condition) {
      this.eq(fieldName, value);
    }
    return typedThis;
  }

  @Override
  public <V> RType in(boolean condition, String fieldName, Collection<V> value) {
    return typedThis;
  }

  @Override
  public <V> RType in(String fieldName, Collection<V> value) {
    return typedThis;
  }

  @Override
  public <V> RType like(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType like(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType notLike(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType notLike(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType likeLeft(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType likeLeft(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType likeRight(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType likeRight(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType isNull(V value) {
    return typedThis;
  }

  @Override
  public <V> RType isNotNull(V value) {
    return typedThis;
  }

  @Override
  public <V> RType gt(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType gt(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType ge(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType ge(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType lt(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType lt(String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType le(boolean condition, String fieldName, V value) {
    return typedThis;
  }

  @Override
  public <V> RType le(String fieldName, V value) {
    return typedThis;
  }
}
