package io.trydent.olimpo.db;

import io.trydent.olimpo.sys.Lazy;
import io.trydent.olimpo.type.Type;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;

import static io.trydent.olimpo.sys.Lazy.lazy;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;

public interface DbClient extends Type.As<SQLClient> {
  static DbClient dbClient(final Vertx vertx, final Json params) {
    return new LazyDbClient(vertx, params);
  }
  static DbClient dbClient(final Vertx vertx, final JdbcUrl jdbcUrl) {
    return dbClient(vertx, jdbcUrl.get());
  }
  static DbClient dbClient(final Vertx vertx, final String url) {
    return dbClient(vertx, json(
      field("url", url)
    ));
  }
  static DbClient dbClient(final Vertx vertx, final DbSource dbSource) {
    return new LazyDbClient(vertx, dbSource);
  }
}

final class LazyDbClient implements DbClient {
  private final Lazy<SQLClient> client;

  LazyDbClient(final Vertx vertx, final Json params) {
    this(
      lazy(() -> JDBCClient.createNonShared(vertx, params.get()))
    );
  }
  LazyDbClient(final Vertx vertx, final DbSource dbSource) {
    this(
      lazy(() -> JDBCClient.create(vertx, dbSource.get()))
    );
  }
  private LazyDbClient(Lazy<SQLClient> client) {
    this.client = client;
  }

  @Override
  public final SQLClient get() {
    return this.client.get();
  }
}
