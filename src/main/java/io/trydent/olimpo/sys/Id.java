package io.trydent.olimpo.sys;

import java.util.UUID;
import java.util.function.Supplier;

public interface Id extends Supplier<String> {
  static Id id(final String value) {
    return new IdImpl(value);
  }

  static Id uuid() {
    return new Uuid();
  }
}

class IdImpl implements Id {
  private final String value;

  IdImpl(final String value) {
    this.value = value;
  }

  @Override
  public final String get() {
    return this.value;
  }
}

class Uuid implements Id {
  @Override
  public final String get() {
    return UUID.randomUUID().toString();
  }
}

