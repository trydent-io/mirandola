package io.trydent.olimpo.db;

import io.trydent.olimpo.io.JdbcUrl;
import io.trydent.olimpo.sys.Lazy;
import io.trydent.olimpo.sys.Property;
import io.trydent.olimpo.type.Type;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;

import static io.trydent.olimpo.sys.Lazy.lazy;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;

public interface SqlClient extends Type.As<SQLClient> {
  static SqlClient dbmsClient(final Vertx vertx, final Json params) {
    return new JdbcClient(vertx, params);
  }
  static SqlClient dbmsClient(final Vertx vertx, final JdbcUrl jdbcUrl) {
    return dbmsClient(vertx, jdbcUrl.get());
  }
  static SqlClient dbmsClient(final Vertx vertx, final String url) {
    return dbmsClient(vertx, json(
      field("url", url)
    ));
  }
}

final class JdbcClient implements SqlClient {
  private final Lazy<SQLClient> client;

  JdbcClient(final Vertx vertx, final Json params) {
    this(
      lazy(() -> JDBCClient.createNonShared(vertx, params.get()))
    );
  }
  private JdbcClient(Lazy<SQLClient> client) {
    this.client = client;
  }

  @Override
  public final SQLClient get() {
    return this.client.get();
  }
}
