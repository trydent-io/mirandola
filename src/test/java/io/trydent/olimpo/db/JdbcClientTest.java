package io.trydent.olimpo.db;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;

import static io.trydent.olimpo.type.When.when;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(VertxExtension.class)
class JdbcClientTest {
  private static final Logger log = getLogger(JdbcClientTest.class);
  private static final String URL = "jdbc:h2:mem:test;MODE=PostgreSQL";

  private final SqlClient sqlClient;

  JdbcClientTest(final Vertx vertx) {
    this.sqlClient = new JdbcClient(
      vertx,
      json(
        field("url", URL)
      )
    );
  }

  @BeforeEach
  void beforeEach() {
    final var db = sqlClient.get();
    db.update("create table test(id int, field varchar(255))", async -> {
      when(async.succeeded(),
        () -> log.info("Table `temp` created."),
        () -> log.error("Table `temp` not created: {}.", async.cause().getMessage()));
      db.update("insert into test(id, field) values(1, 'value1')", asyncUpd1 ->
        when(asyncUpd1.succeeded(),
          () -> log.info("Row in `temp` created."),
          () -> log.error("Row in `temp` not created: {}.", asyncUpd1.cause().getMessage()))
      );
      db.update("insert into test(id, field) values(2, 'value2')", asyncUpd2 ->
        when(asyncUpd2.succeeded(),
          () -> log.info("Row in `temp` created."),
          () -> log.error("Row in `temp` not created: {}.", asyncUpd2.cause().getMessage()))
      );
    });
  }

  @Test
  @DisplayName("should get a sql-client")
  void shouldGetSqlClient() {
    final var db = sqlClient.get();

    db.query("select id, field from temp", async ->
      when(async.succeeded(),
        () -> log.info(async.result().getRows().toString()),
        () -> log.error("Failed to retrieve rows.")
      )
    );
    assertThat(sqlClient.get()).isNotNull();
  }
}
