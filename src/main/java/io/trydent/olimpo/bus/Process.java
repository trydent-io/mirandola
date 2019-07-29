package io.trydent.olimpo.bus;

import io.trydent.olimpo.sys.Id;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.function.BiConsumer;

import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;

public interface Process extends BiConsumer<String, Execution> {
  static Process commandProcess(Id id, EventBus bus) {
    return new CommandProcess(id, bus);
  }
}

final class CommandProcess implements Process {
  private final Id id;
  private final EventBus bus;

  CommandProcess(final Id id, final EventBus bus) {
    this.id = id;
    this.bus = bus;
  }

  @Override
  public final void accept(String name, Execution execution) {
    bus.<JsonObject>localConsumer(name, message -> {
      message.reply(
        json(
          field("id", id.get())
        ).get()
      );
      execution.accept(message.body());
    });
  }
}
