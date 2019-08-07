package io.trydent.olimpo.db;

import io.vertx.core.Vertx;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.h2.Driver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static io.trydent.olimpo.db.DbClient.dbClient;
import static io.trydent.olimpo.db.DbParams.jdbcUrl;
import static io.trydent.olimpo.db.DbSource.dbSource;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
class MigratedDbSourceTest {

  @Test
  @Timeout(value = 6, timeUnit = SECONDS)
  void shouldMigrate(Vertx vertx, VertxTestContext test) {
    final var dbClient = dbClient(
      vertx,
      new MigratedDbSource(
        dbSource(jdbcUrl("jdbc:h2:mem:test", Driver.class))
      )
    );

    dbClient.get().update("insert into readings(id, reading) values ('uuid', 'reading')", async ->
      dbClient.get().query("select * from readings", async2 -> {
        if (async2.failed()) async2.cause().printStackTrace();
        assertThat(async2.result().getRows().size()).isEqualTo(1);
        test.completeNow();
      })
    );
  }
}
