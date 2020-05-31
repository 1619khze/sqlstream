package org.sqlstream;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

import java.util.Objects;

/**
 * @author WangYi
 * @since 2020/5/26
 */
public final class SqlStreamBuilder {
  private HikariConfig hikariConfig;
  private HikariDataSource dataSource;
  private Sql2o sql2o;
  private String jdbcUrl;
  private String username;
  private String password;
  private String driverClassName;

  private SqlStreamBuilder() {
  }

  private SqlStreamBuilder(HikariConfig hikariConfig) {
    this(hikariConfig, new HikariDataSource());
  }

  private SqlStreamBuilder(HikariConfig hikariConfig, HikariDataSource dataSource) {
    this.hikariConfig = hikariConfig;
    this.dataSource = dataSource;
  }

  public SqlStreamBuilder jdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
    return this;
  }

  public SqlStreamBuilder username(String username) {
    this.username = username;
    return this;
  }

  public SqlStreamBuilder password(String password) {
    this.password = password;
    return this;
  }

  public SqlStreamBuilder driver(String driverClassName) {
    this.driverClassName = driverClassName;
    return this;
  }

  public static SqlStreamBuilder builder() {
    return new SqlStreamBuilder();
  }

  public SqlStream build() {
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
    return new SqlStream(sql2o);
  }
}
