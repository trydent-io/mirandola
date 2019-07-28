package io.trydent.olimpo.sys;

import io.trydent.olimpo.type.Type;

public interface Property extends Type.AsString {
  static Property envVar(final String name) {
    return new EnvVar(name);
  }
}

final class EnvVar implements Property {
  private final String name;

  EnvVar(String name) {
    this.name = name;
  }

  @Override
  public final String get() {
    return System.getenv(name);
  }
}
