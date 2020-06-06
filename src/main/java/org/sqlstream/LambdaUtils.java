package org.sqlstream;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author WangYi
 * @since 2020/6/5
 */
public final class LambdaUtils {
  private LambdaUtils() {
  }

  public static String getLambdaColumnName(Serializable lambda) {
    try {
      Method method = lambda.getClass().getDeclaredMethod("writeReplace");
      method.setAccessible(Boolean.TRUE);
      SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
      String getter = serializedLambda.getImplMethodName();
      return Introspector.decapitalize(getter.replace("get", ""));
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
