package org.sqlstream;

import java.lang.annotation.*;

/**
 * @author WangYi
 * @since 2020/5/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
  String value() default "";
}
