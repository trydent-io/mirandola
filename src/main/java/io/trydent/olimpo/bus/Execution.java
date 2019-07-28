package io.trydent.olimpo.bus;

import io.vertx.core.json.JsonObject;

import java.util.function.Consumer;

@FunctionalInterface
public interface Execution extends Consumer<JsonObject> {}
