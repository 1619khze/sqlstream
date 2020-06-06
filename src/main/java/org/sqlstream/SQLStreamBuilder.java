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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

import java.util.Objects;

/**
 * @author WangYi
 * @since 2020/5/26
 */
public final class SQLStreamBuilder {
  private HikariConfig hikariConfig;
  private HikariDataSource dataSource;
  private Sql2o sql2o;
  private String jdbcUrl;
  private String username;
  private String password;
  private String driverClassName;

  public static SQLStreamBuilder builder() {
    return new SQLStreamBuilder();
  }

  public SQLStreamBuilder jdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
    return this;
  }

  public SQLStreamBuilder username(String username) {
    this.username = username;
    return this;
  }

  public SQLStreamBuilder password(String password) {
    this.password = password;
    return this;
  }

  public SQLStreamBuilder driver(String driverClassName) {
    this.driverClassName = driverClassName;
    return this;
  }

  public SQLStream build() {
    if (Objects.isNull(hikariConfig)) {
      this.hikariConfig = new HikariConfig();
      this.hikariConfig.setJdbcUrl(jdbcUrl);
      this.hikariConfig.setUsername(username);
      this.hikariConfig.setPassword(password);
      this.hikariConfig.setDriverClassName(driverClassName);
    }
    if (Objects.isNull(dataSource)) {
      this.dataSource = new HikariDataSource(hikariConfig);
    }
    if (Objects.isNull(sql2o)) {
      this.sql2o = new Sql2o(this.dataSource);
    }
    return new SQLStream(sql2o);
  }
}
