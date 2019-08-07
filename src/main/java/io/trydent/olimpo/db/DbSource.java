package io.trydent.olimpo.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.trydent.olimpo.type.Type;

import javax.sql.DataSource;

public interface DbSource extends Type.As<DataSource> {
  static DbSource dbSource(final DbParams params) {
    return new HikariSource(params);
  }
}

final class HikariSource implements DbSource {
  private final DbParams params;

  HikariSource(final DbParams params) {
    this.params = params;
  }

  @Override
  public final DataSource get() {
    final var json = params.get();
    final var config = new HikariConfig();
    config.setDriverClassName(json.getString("driver_class"));
    config.setUsername(json.getString("user"));
    config.setPassword(json.getString("password"));
    config.setJdbcUrl(json.getString("url"));
    return new HikariDataSource(config);
  }
}
