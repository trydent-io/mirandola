package io.trydent.olimpo.sink;

import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;

@FunctionalInterface
public interface Command extends Consumer<JsonObject> {
  @Override
  default void accept(JsonObject params) {
    this.execute(params);
  }

  void execute(JsonObject params);
}
