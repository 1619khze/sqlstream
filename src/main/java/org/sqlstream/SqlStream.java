package org.sqlstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * @author WangYi
 * @since 2020/5/30
 */
public final class SqlStream {
  private static final Logger log = LoggerFactory.getLogger(SqlStream.class);

  private final Sql2o sql2o;
  private final StringBuilder querySql = new StringBuilder();

  private static final String select = "select ";
  private static final String selectAll = "select *";
  private static final String from = " from ";
  private static final String where = " where ";
  private static final String or = " or ";
  private static final String and = " and ";
  private static final String eq = " = ";
  private static final String ne = " <> ";
  private static final String distinct = " distinct ";
  private static final String comma = ",";

  private final List<String> whereConditions = new ArrayList<>();

  SqlStream(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public final SqlStream selectAll() {
    this.querySql.append(selectAll);
    return this;
  }

  public final SqlStream select() {
    this.querySql.append(select);
    return this;
  }

  public final SqlStream select(String... fieldNames) {
    requireNonNull(fieldNames, "fileNames cannot be set to null");
    if (fieldNames.length == 0) {
      return select();
    }
    this.querySql.append(select)
            .append(String.join(comma, fieldNames));
    return this;
  }

  @SafeVarargs
  public final <T, R> SqlStream select(TypeFunction<T, R>... typeFunctions) {
    requireNonNull(typeFunctions, "fieldFunctions cannot be set to null");
    return select(Arrays.stream(typeFunctions)
            .map(this::getLambdaColumnName)
            .collect(joining(",")));
  }

  public final SqlStream distinct(String... fieldNames) {
    requireNonNull(fieldNames, "fileNames cannot be set to null");
    this.querySql.append(distinct).append(
            String.join(comma, fieldNames));
    return this;
  }

  public final <T, R> SqlStream distinct(TypeFunction<T, R>... typeFunctions) {
    this.distinct(Arrays.stream(typeFunctions)
            .map(this::getLambdaColumnName)
            .collect(joining(comma)));
    return this;
  }

  public final SqlStream where() {
    this.querySql.append(where);
    return this;
  }

  public final SqlStream eq(String fieldName, Object value) {
    this.addOperator(fieldName, value, eq);
    return this;
  }

  public final <T, R> SqlStream eq(TypeFunction<T, R> typeFunction, Object value) {
    this.eq(getLambdaColumnName(typeFunction), value);
    return this;
  }

  private void addOperator(String fieldName, Object value, String operator) {
    requireNonNull(fieldName, "fieldName cannot be set to null");
    requireNonNull(value, "value cannot be set to null");
    requireNonNull(operator, "operator cannot be set to null");

    String matchSymbol = "'%s'";
    if (value instanceof Number) {
      matchSymbol = "%d";
    }
    String condition = String.format(fieldName + operator + matchSymbol, value);
    this.whereConditions.add(condition);
  }

  public final SqlStream ne(String fieldName, Object value) {
    this.addOperator(fieldName, value, ne);
    return this;
  }

  public final <P, T, R> SqlStream ne(Predicate<P> condition, TypeFunction<T, R> typeFunction, P value) {
    if (condition.test(value)) {
      String lambdaColumnName = this.getLambdaColumnName(typeFunction);
      this.addOperator(lambdaColumnName, value, ne);
    }
    return this;
  }

  private String getLambdaColumnName(Serializable lambda) {
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

  public final <T> List<T> from(Class<T> returnType) {
    requireNonNull(returnType, "The return type must be specified");
    this.querySql.append(from).append(returnType.getSimpleName().toLowerCase());
    if (!whereConditions.isEmpty()) {
      if (!this.querySql.toString().contains(where.trim())) {
        this.querySql.append(where);
      }
      this.querySql.append(String.join(and, this.whereConditions));
    }
    log.info("The output sql is：" + querySql.toString());
    try (Connection open = sql2o.open()) {
      return open.createQuery(querySql.toString()).executeAndFetch(returnType);
    } finally {
      this.querySql.delete(0, querySql.length());
      this.whereConditions.clear();
    }
  }
}
