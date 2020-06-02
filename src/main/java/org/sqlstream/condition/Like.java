package org.sqlstream.condition;

import java.io.Serializable;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Like<R, RType> extends Serializable {
  <V> RType like(boolean condition, R typeFunction, V value);

  <V> RType like(boolean condition, String fieldName, V value);

  <V> RType like(R typeFunction, V value);

  <V> RType like(String fieldName, V value);

  <V> RType notLike(boolean condition, R typeFunction, V value);

  <V> RType notLike(boolean condition, String fieldName, V value);

  <V> RType notLike(R typeFunction, V value);

  <V> RType notLike(String fieldName, V value);

  <V> RType likeLeft(boolean condition, R typeFunction, V value);

  <V> RType likeLeft(boolean condition, String fieldName, V value);

  <V> RType likeLeft(R typeFunction, V value);

  <V> RType likeLeft(String fieldName, V value);

  <V> RType likeRight(boolean condition, R typeFunction, V value);

  <V> RType likeRight(boolean condition, String fieldName, V value);

  <V> RType likeRight(R typeFunction, V value);

  <V> RType likeRight(String fieldName, V value);
}
