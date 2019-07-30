package io.trydent.olimpo.bus;

import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;

@FunctionalInterface
public interface Execution extends Consumer<JsonObject> {
  @Override
  default void accept(JsonObject params) {
    this.execute(params);
  }

  void execute(JsonObject params);
}
