package io.trydent.olimpo.action;

import io.trydent.olimpo.sys.Id;
import io.trydent.olimpo.vertx.Address;
import io.trydent.olimpo.vertx.Delivery;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.function.BiFunction;

import static io.trydent.olimpo.sys.Id.id;
import static io.trydent.olimpo.vertx.Address.templateAddress;
import static io.trydent.olimpo.vertx.Delivery.localDelivery;
import static java.lang.String.format;

public interface Action extends BiFunction<String, Json, Promise<Id>> {
  private static Address command() {
    return templateAddress("%s-command");
  }

  static Action busCommand(final EventBus bus) {
    return new BusCommand(
      bus,
      command(),
      localDelivery()
    );
  }
}

final class BusCommand implements Action {
  private final EventBus bus;
  private final Address command;
  private final Delivery delivery;

  BusCommand(final EventBus bus, final Address command, final Delivery delivery) {
    this.bus = bus;
    this.command = command;
    this.delivery = delivery;
  }

  @Override
  public final Promise<Id> apply(String name, Json params) {
    final var promise = Promise.<Id>promise();
    bus.<JsonObject>request(command.apply(name), params.get(), delivery.get(), async -> {
        if (async.succeeded()) {
          promise.complete(id(async.result().body().getString("id")));
        }
      }
    );
    return promise;
  }
}
