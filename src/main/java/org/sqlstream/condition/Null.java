package org.sqlstream.condition;

import java.io.Serializable;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public interface Null<R, RType> extends Serializable {
  <V> RType isNull(V value);

  <V> RType isNotNull(V value);
}
