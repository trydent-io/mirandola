package io.trydent.olimpo.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.trydent.olimpo.sys.Lazy;
import io.trydent.olimpo.type.Type;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

import static io.trydent.olimpo.sys.Lazy.*;

public interface DbSource extends Type.As<DataSource> {
  static DbSource jdbcSource(final DbParams params) {
    return new JdbcSource(params);
  }

  static DbSource dbSource(final DbParams params) {
    return new LazyDbSource(new JdbcSource(params));
  }

  static DbSource migratedDbSource(final DbParams params) {
    return new MigratedDbSource(dbSource(params));
  }
}

final class JdbcSource implements DbSource {
  private final DbParams params;

  JdbcSource(final DbParams params) {
    this.params = params;
  }

  @Override
  public final DataSource get() {
    final var json = params.get();
    final var config = new HikariConfig();
    config.setDriverClassName(json.getString("driver_class", ""));
    config.setUsername(json.getString("user", ""));
    config.setPassword(json.getString("password", ""));
    config.setJdbcUrl(json.getString("url"));
    return new HikariDataSource(config);
  }
}

final class LazyDbSource implements DbSource {
  private final Lazy<DataSource> dbSource;

  LazyDbSource(final DbSource dbSource) {
    this(lazy(dbSource));
  }

  private LazyDbSource(Lazy<DataSource> dbSource) {
    this.dbSource = dbSource;
  }

  @Override
  public final DataSource get() {
    return dbSource.get();
  }
}

final class MigratedDbSource implements DbSource {
  private final DbSource dbSource;

  MigratedDbSource(final DbSource dbSource) {
    this.dbSource = dbSource;
  }

  @Override
  public DataSource get() {
    final var dataSource = dbSource.get();
    final var migrated = Flyway
      .configure()
      .dataSource(dataSource)
      .load()
      .migrate();
    return dataSource;
  }
}
