package org.sqlstream.condition;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface In<R, RType> extends Serializable {
  <V> RType in(boolean condition, R typeFunction, Collection<V> value);

  <V> RType in(boolean condition, String fieldName, Collection<V> value);
}
