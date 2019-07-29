package io.trydent.olimpo.bus;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.trydent.olimpo.sys.Id.id;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandProcessTest {
  private interface JsonMessageConsumer extends MessageConsumer<JsonObject> {}

  private final EventBus eventBus = mock(EventBus.class);
  private final MessageConsumer<JsonObject> consumer = mock(JsonMessageConsumer.class);

  @Test
  @DisplayName("should execute a command")
  void shouldExecuteCommand() {
    when(eventBus.<JsonObject>localConsumer(eq("any-command"), any())).thenReturn(consumer);

    final var id = id("process-id");
    final var process = new CommandProcess(
      id,
      eventBus
    );

    process.accept("any-command", params -> {});

    verify(eventBus).<JsonObject>localConsumer(eq("any-command"), any());
  }
}
