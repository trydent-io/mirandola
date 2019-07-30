package io.trydent.olimpo.apollo;

import io.trydent.olimpo.action.Action;
import io.trydent.olimpo.bus.Command;
import io.trydent.olimpo.bus.Process;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.trydent.olimpo.action.Action.busCommand;
import static io.trydent.olimpo.bus.Process.commandProcess;
import static io.trydent.olimpo.db.DbmsClient.database;
import static io.trydent.olimpo.sys.Id.*;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(VertxExtension.class)
class AddReadingTest {
  private final Command command;
  private final Process process;
  private final EventBus bus;
  private final Action action;

  AddReadingTest(final Vertx vertx) {
    this.bus = vertx.eventBus();
    this.command = new AddReading(
      database(
        vertx,
        json(
          field("url", "jdbc:h2:mem:test")
        )
      )
    );
    this.process = commandProcess(
      id("uniq"),
      vertx.eventBus()
    );
    this.action = busCommand(bus);
  }


  @Test
  @Timeout(value = 4, timeUnit = SECONDS)
  void shouldAddReading(VertxTestContext test) {
    bus.<JsonObject>localConsumer("add-reading-processed", message -> {
      assertThat(message.body()).isNotNull();
      test.completeNow();
    });

    command.accept(process);

    action.apply("add-reading", json(
      field("param", "whatever")
    ));
  }
}
