package org.sqlstream;

import java.lang.annotation.*;

/**
 * @author WangYi
 * @since 2020/5/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
  String value() default "";

  IdType type() default IdType.AUTO;
}
