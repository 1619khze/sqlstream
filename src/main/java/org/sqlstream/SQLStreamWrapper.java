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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.sqlstream.SQLConstants.*;

/**
 * @author WangYi
 * @since 2020/6/2
 */
public class SQLStreamWrapper<T> extends AbstractSQLBuilder<T, TypeFunction<T, ?>, SQLStreamWrapper<T>> implements JDBCResult<T> {
  private static final Logger log = LoggerFactory.getLogger(SQLStreamWrapper.class);

  private final Class<T> entityClass;
  private final StringBuilder querySql;
  private final Sql2o sql2o;

  public SQLStreamWrapper(Class<T> entityClass, Sql2o sql2o, StringBuilder querySql) {
    this.entityClass = entityClass;
    this.sql2o = sql2o;
    this.querySql = querySql;
  }

  private void preProcessing(Class<T> returnType) {
    requireNonNull(returnType, "The return type must be specified");
    this.confirmTableName();
    this.combinationConditions();
    log.info("The output sql is：" + this.querySql.toString());
  }

  private void combinationConditions() {
    if (!whereConditions.isEmpty()) {
      if (!this.querySql.toString().contains(where.trim())) {
        this.querySql.append(where);
      }
      this.querySql.append(String.join(and, whereConditions));
    }
  }

  private void confirmTableName() {
    this.querySql.append(from);
    String tableName = entityClass.getSimpleName().toLowerCase();
    if (entityClass.isAnnotationPresent(Table.class)) {
      Table table = entityClass.getDeclaredAnnotation(Table.class);
      if (!"".equals(table.value())) {
        tableName = table.value();
      }
    }
    this.querySql.append(tableName);
  }

  @Override
  public List<T> list() {
    this.preProcessing(entityClass);
    try (Connection connection = sql2o.open()) {
      return connection.createQuery(this.querySql.toString())
              .executeAndFetch(entityClass);
    } finally {
      release();
    }
  }

  @Override
  public T one() {
    this.preProcessing(entityClass);
    try (Connection connection = sql2o.open()) {
      return connection.createQuery(this.querySql.toString())
              .executeAndFetchFirst(entityClass);
    } finally {
      release();
    }
  }

  @Override
  public Long size() {
    this.preProcessing(entityClass);
    try (Connection connection = sql2o.open()) {
      String sql = this.querySql.toString();
      if(sql.startsWith("select count")){
        return connection.createQuery(sql)
                .executeScalar(Long.class);
      }
      return (long) connection.createQuery(sql)
              .executeAndFetch(entityClass).size();
    } finally {
      release();
    }
  }

  private void release() {
    this.querySql.delete(0, this.querySql.length());
    whereConditions.clear();
  }
}
