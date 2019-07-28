package io.trydent.olimpo.vertx.json;

import io.trydent.olimpo.type.Type;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface Json extends Type.As<JsonObject> {
  interface Field<String, T> extends Consumer<Map<java.lang.String, Object>> {
    static <T> Field<java.lang.String, T> field(java.lang.String key, T value) {
      return new JsonField<>(key, value);
    }
  }

  @SafeVarargs
  static Json json(final Field<String, ?>... fields) {
    return new JsonImpl(Arrays.copyOf(fields, fields.length));
  }

  static Json json(final Buffer buffer) {
    return new BufferJson(buffer);
  }
}

final class JsonField<T> implements Json.Field<String, T> {
  private final String key;
  private final T value;

  JsonField(final String key, final T value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public void accept(final Map<String, Object> map) {
    map.put(key, value);
  }
}

final class JsonImpl implements Json {
  private final Field<String, ?>[] fields;

  JsonImpl(final Field<String, ?>[] fields) {
    this.fields = fields;
  }

  @Override
  public JsonObject get() {
    final var map = new HashMap<String, Object>();
    for (var field : fields) field.accept(map);
    return JsonObject.mapFrom(map);
  }
}

final class BufferJson implements Json {
  private final Buffer buffer;

  BufferJson(final Buffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public final JsonObject get() {
    return buffer.toJsonObject();
  }
}
