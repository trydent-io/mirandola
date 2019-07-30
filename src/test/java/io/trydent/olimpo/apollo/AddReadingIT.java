package io.trydent.olimpo.apollo;

import io.trydent.olimpo.action.Action;
import io.trydent.olimpo.bus.Bus;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.trydent.olimpo.action.Action.busCommand;
import static io.trydent.olimpo.apollo.ApolloCommand.addReading;
import static io.trydent.olimpo.bus.Bus.commandBus;
import static io.trydent.olimpo.db.DbmsClient.dbmsClient;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
class AddReadingIT {
  private final Bus commandBus;
  private final EventBus bus;
  private final Action action;

  AddReadingIT(final Vertx vertx) {
    this.bus = vertx.eventBus();
    this.commandBus = commandBus(
      vertx.eventBus(),
      addReading(
        dbmsClient(
          vertx,
          json(
            field("url", "jdbc:h2:mem:test")
          )
        )
      )
    );
    this.action = busCommand(bus);
  }


  @Test
  @DisplayName("should submit add-reading then add-reading is processed")
  @Timeout(value = 4, timeUnit = SECONDS)
  void shouldAddReading(VertxTestContext test) {
    bus.<JsonObject>localConsumer("add-reading-processed", message -> {
      assertThat(message.body()).isNotNull();
      test.completeNow();
    });

    action.submit(
      "add-reading",
      json(
        field("param", "whatever")
      )
    );
  }
}
