package io.trydent.olimpo.vertx.async;

import io.trydent.olimpo.type.Type;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public interface AsyncJsonMessage extends Type.As<JsonObject> {
  static AsyncJsonMessage $(AsyncResult<Message<JsonObject>> result) {
    return new ExtendedAsyncJsonMessage(result);
  }

  default String stringField(String key) {
    return get().getString(key);
  }
}

final class ExtendedAsyncJsonMessage implements AsyncJsonMessage {
  private final AsyncResult<Message<JsonObject>> result;

  ExtendedAsyncJsonMessage(AsyncResult<Message<JsonObject>> result) {
    this.result = result;
  }

  @Override
  public final JsonObject get() {
    return result.result().body();
  }
}
