package org.sqlstream.condition;

import java.io.Serializable;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Between<R, RType> extends Serializable {
  <V> RType between(boolean condition, R typeFunction, V value1, V value2);

  <V> RType between(boolean condition, String fieldName, V value1, V value2);

  <V> RType between(R typeFunction, V value1, V value2);

  <V> RType between(String fieldName, V value1, V value2);

  <V> RType notBetween(boolean condition, R typeFunction, V value1, V value2);

  <V> RType notBetween(boolean condition, String fieldName, V value1, V value2);

  <V> RType notBetween(R typeFunction, V value1, V value2);

  <V> RType notBetween(String fieldName, V value1, V value2);
}
