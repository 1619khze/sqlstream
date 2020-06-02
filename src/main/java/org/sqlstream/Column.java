package org.sqlstream;

import java.lang.annotation.*;

/**
 * @author WangYi
 * @since 2020/6/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
  String value() default "";

  boolean exists() default true;
}
