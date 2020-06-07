/*
 * MIT License
 *
 * Copyright (c) 2019 1619kHz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.sqlstream;

import org.sql2o.Sql2o;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.sqlstream.SQLConstants.*;

/**
 * @author WangYi
 * @since 2020/5/30
 */
public final class SQLStream {
  private final Sql2o sql2o;
  private final StringBuilder sqlAction = new StringBuilder();

  SQLStream(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  public final SQLStream select() {
    this.sqlAction.append(selectAll);
    return this;
  }

  public final SQLStream select(String... fieldNames) {
    requireNonNull(fieldNames, "fileNames cannot be set to null");
    if (fieldNames.length == 0) {
      return select();
    }
    this.sqlAction.append(select).append(String.join(comma, fieldNames));
    return this;
  }

  public final SQLStream distinct(String... fieldNames) {
    requireNonNull(fieldNames, "fileNames cannot be set to null");
    if (this.sqlAction.toString().startsWith(selectAll)) {
      sqlAction.replace(0, selectAll.length(), select);
    }
    this.sqlAction.append(distinct).append(
            String.join(comma, fieldNames));
    return this;
  }

  public final SQLStream count(String fieldNames) {
    requireNonNull(fieldNames, "fileNames cannot be set to null");
    if (sqlAction.toString().startsWith(selectAll)) {
      sqlAction.replace(0, selectAll.length(), select);
    }
    sqlAction.append(count).append(leftParenthesis).append(
            String.join(comma, fieldNames)).append(rightParenthesis);
    return this;
  }

  public final SQLStream count() {
    return count(asterisk);
  }

  public final <T, R> SQLStream count(TypeFunction<T, R> typeFunctions) {
    requireNonNull(typeFunctions, "fieldFunctions cannot be set to null");
    return count(LambdaUtils.getLambdaColumnName(typeFunctions));
  }

  @SafeVarargs
  public final <T, R> SQLStream select(TypeFunction<T, R>... typeFunctions) {
    requireNonNull(typeFunctions, "fieldFunctions cannot be set to null");
    return select(Arrays.stream(typeFunctions)
            .map(LambdaUtils::getLambdaColumnName)
            .collect(joining(",")));
  }

  @SafeVarargs
  public final <T, R> SQLStream distinct(TypeFunction<T, R>... typeFunctions) {
    this.distinct(Arrays.stream(typeFunctions)
            .map(LambdaUtils::getLambdaColumnName)
            .collect(joining(comma)));
    return this;
  }

  public Sql2o getSql2o() {
    return sql2o;
  }

  public <T> SQLStreamWrapper<T> from(Class<T> entityClass) {
    return new SQLStreamWrapper<>(entityClass, sql2o, sqlAction);
  }
}
