package io.trydent.olimpo.vertx.json;

import io.trydent.olimpo.type.Type;

import java.util.Arrays;

public interface JsonString extends Type.AsString {
  static JsonString stringJson(Json.Field<String, ?>... fields) {
    return new JsonStringImpl(
      new JsonImpl(
        Arrays.copyOf(fields, fields.length)
      )
    );
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
