package org.sqlstream;

import java.util.List;

/**
 * @author WangYi
 * @since 2020/6/6
 */
public interface JDBCResult<T> {
  List<T> list();

  T one();
}
