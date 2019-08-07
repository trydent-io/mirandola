package io.trydent.olimpo.db;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.trydent.olimpo.db.DbSource.dbSource;

class MigratedDbSourceTest {
  private final DbParams params = () -> new JsonObject().put("url", "jdbc:h2:mem:test");
  private final DbSource source = dbSource(params);

  @Test
  void shouldMigrate() {

  }
}
