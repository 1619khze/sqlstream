package org.sqlstream.condition;

import java.io.Serializable;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Size<R, RType> extends Serializable {
  <V> RType gt(boolean condition, R typeFunction, V value);

  <V> RType gt(boolean condition, String fieldName, V value);

  <V> RType gt(R typeFunction, V value);

  <V> RType gt(String fieldName, V value);

  <V> RType ge(boolean condition, R typeFunction, V value);

  <V> RType ge(boolean condition, String fieldName, V value);

  <V> RType ge(R typeFunction, V value);

  <V> RType ge(String fieldName, V value);

  <V> RType lt(boolean condition, R typeFunction, V value);

  <V> RType lt(boolean condition, String fieldName, V value);

  <V> RType lt(R typeFunction, V value);

  <V> RType lt(String fieldName, V value);

  <V> RType le(boolean condition, R typeFunction, V value);

  <V> RType le(boolean condition, String fieldName, V value);

  <V> RType le(R typeFunction, V value);

  <V> RType le(String fieldName, V value);
}
