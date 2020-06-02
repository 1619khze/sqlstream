package org.sqlstream.condition;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Compare<R, RType> extends Serializable {
  default <V> RType allEq(Map<R, V> params) {
    return this.allEq(params, true);
  }

  <V> RType allEq(Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(BiPredicate<R, V> filter, Map<R, V> params);

  <V> RType allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(boolean condition, Map<R, V> params, boolean null2IsNull);

  <V> RType allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull);

  <V> RType ne(boolean condition, R typeFunction, V value);

  <V> RType ne(boolean condition, String fieldName, V value);

  <V> RType ne(R typeFunction, V value);

  <V> RType ne(String fieldName, V value);

  <V> RType eq(boolean condition, R typeFunction, V value);

  <V> RType eq(R typeFunction, Object value);

  <V> RType eq(String fieldName, V value);

  <V> RType eq(boolean condition, String fieldName, V value);
}