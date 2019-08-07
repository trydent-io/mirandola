package io.trydent.olimpo.sink;

import io.trydent.olimpo.sys.Id;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.function.BiFunction;

import static io.trydent.olimpo.sink.MetaCommand.metaCommand;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;

public interface CommandSink extends BiFunction<String, Command, CommandSink> {
  static CommandSink commandBus(final EventBus bus, final Id id) {
    return new CommandBus(id, bus);
  }

  CommandSink let(String name, Command command);

  @Override
  default CommandSink apply(String name, Command command) {
    return this.let(name, command);
  }
}

final class CommandBus implements CommandSink {
  private final Id id;
  private final EventBus bus;

  CommandBus(final Id id, final EventBus bus) {
    this.id = id;
    this.bus = bus;
  }

  private CommandSink let(final MetaCommand... metas) {
    for (var metaCommand : metas) {
      metaCommand.process((name, command) ->
        bus.<JsonObject>localConsumer(name, message -> {
          message.reply(
            json(
              field("id", id.get())
            ).get()
          );
          command.execute(message.body());
          bus.publish(name.replaceAll("command", "processed"), new JsonObject());
        })
      );
    }
    return this;
  }

  @Override
  public CommandSink let(String name, Command command) {
    return let(metaCommand(name, command));
  }
}

