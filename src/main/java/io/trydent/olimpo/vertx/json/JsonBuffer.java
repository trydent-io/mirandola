package io.trydent.olimpo.vertx.json;

import io.trydent.olimpo.type.Type;
import io.vertx.core.buffer.Buffer;

import java.util.Arrays;

public interface JsonBuffer extends Type.As<Buffer> {
  @SafeVarargs
  static JsonBuffer jsonBuffer(final Json.Field<String, ?>... fields) {
    return new JsonBufferImpl(
      new JsonImpl(
        Arrays.copyOf(fields, fields.length)
      )
    );
  }
}

final class JsonBufferImpl implements JsonBuffer {
  private final Json json;

  JsonBufferImpl(final Json json) {
    this.json = json;
  }

  @Override
  public final Buffer get() {
    return json.get().toBuffer();
  }
}
