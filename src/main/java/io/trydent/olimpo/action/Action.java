package io.trydent.olimpo.action;

import io.trydent.olimpo.sys.Id;
import io.trydent.olimpo.vertx.Delivery;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static io.trydent.olimpo.action.Async.*;
import static io.trydent.olimpo.sys.Id.id;
import static io.trydent.olimpo.vertx.Delivery.localDelivery;
import static io.trydent.olimpo.vertx.async.AsyncJsonMessage.$;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

public interface Action extends BiFunction<String, Json, Promise<Id>> {

  static Action commandAction(final EventBus bus) {
    return new CommandAction(
      bus,
      "%s-command",
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
  private final String command;
  private final Delivery delivery;

  CommandAction(final EventBus bus, final String command, final Delivery delivery) {
    this.bus = bus;
    this.command = command;
    this.delivery = delivery;
  }

  @Override
  public final Promise<Id> submit(String name, Json params) {
    final var promise = Promise.<Id>promise();
    final var commandName = format(command, name);
    awaitMessage(async -> bus.request("asd", new JsonObject(), async))
    .future()
    .map(Message::body)
    .map(json -> json.getString("id"))
    .map(Id::id);
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

interface AsyncResult<T> extends Consumer<Promise<T>> {}
interface AsyncMessage extends Consumer<Promise<Message<JsonObject>>> {}

interface Async {
  static <T> Promise<T> await(AsyncResult<T> result) {
    final var promise = Promise.<T>promise();
    result.accept(promise);
    return promise;
  }

  static Promise<Message<JsonObject>> awaitMessage(AsyncMessage message) {
    final var promise = Promise.<Message<JsonObject>>promise();
    message.accept(promise);
    return promise;
  }
}
