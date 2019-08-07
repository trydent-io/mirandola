package io.trydent.olimpo.action;

import io.trydent.olimpo.sys.Id;
import io.trydent.olimpo.vertx.Delivery;
import io.trydent.olimpo.vertx.Template;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;

import java.util.function.BiFunction;

import static io.trydent.olimpo.sys.Id.id;
import static io.trydent.olimpo.vertx.Delivery.localDelivery;
import static io.trydent.olimpo.vertx.Template.template;
import static io.trydent.olimpo.vertx.async.AsyncJsonMessage.$;
import static org.slf4j.LoggerFactory.getLogger;

public interface Action extends BiFunction<String, Json, Promise<Id>> {

  static Action commandAction(final EventBus bus) {
    return new CommandAction(
      bus,
      template("%s-command"),
      localDelivery()
    );
  }

  @Override
  default Promise<Id> apply(String name, Json params) {
    return this.submit(name, params);
  }

  Promise<Id> submit(String name, Json params);
}

final class CommandAction implements Action {
  private static final Logger log = getLogger(CommandAction.class);

  private final EventBus bus;
  private final Template command;
  private final Delivery delivery;

  CommandAction(final EventBus bus, final Template command, final Delivery delivery) {
    this.bus = bus;
    this.command = command;
    this.delivery = delivery;
  }

  @Override
  public final Promise<Id> submit(String name, Json params) {
    final var promise = Promise.<Id>promise();
    final var commandName = command.apply(name);
    bus.<JsonObject>request(commandName, params.get(), delivery.get(), async -> {
        if (async.succeeded()) {
          promise.complete(id($(async).stringField("id")));
        } else if (async.failed()) {
          log.error("Failed to request `{}`: {}.", commandName, async.cause().getMessage());
          promise.fail(async.cause().getMessage());
        }
      }
    );
    return promise;
  }
}
