package org.sqlstream;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author WangYi
 * @since 2020/5/31
 */
@FunctionalInterface
public interface TypeFunction<T, R> extends Serializable, Function<T, R> {

}
