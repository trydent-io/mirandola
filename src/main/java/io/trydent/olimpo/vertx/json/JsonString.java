package io.trydent.olimpo.vertx.json;

import io.trydent.olimpo.type.Type;

import java.util.Arrays;

public interface JsonString extends Type.AsString {
  @SafeVarargs
  static JsonString jsonString(Json.Field<String, ?>... fields) {
    return new JsonStringImpl(
      new JsonImpl(
        Arrays.copyOf(fields, fields.length)
      )
    );
  }
  static JsonString jsonString(Json json) {
    return new JsonStringImpl(json);
  }
}

final class JsonStringImpl implements JsonString {
  private final Json json;

  JsonStringImpl(final Json json) {
    this.json = json;
  }

  @Override
  public final String get() {
    return json.get().toString();
  }
}
