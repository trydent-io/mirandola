package io.trydent.olimpo.io;

import io.trydent.olimpo.sys.Property;
import io.trydent.olimpo.type.Type;
import org.slf4j.Logger;

import java.util.Optional;

import static io.trydent.olimpo.sys.Property.envVar;
import static org.slf4j.LoggerFactory.getLogger;

public interface Port extends Type.AsInt {
  static Port envPort(String name, int orDefault) {
    return new EnvPort(envVar(name), portOrDie(orDefault));
  }

  static Optional<Port> port(int value) {
    return Optional.of(value)
      .filter(it -> it > 0)
      .map(PortImpl::new);
  }

  static Port portOrDie(int port) {
    return port(port).orElseThrow(IllegalArgumentException::new);
  }
}

final class PortImpl implements Port {
  private final int port;

  PortImpl(final int port) {
    this.port = port;
  }

  @Override
  public final int get() {
    return port;
  }
}

final class EnvPort implements Port {
  private static final Logger log = getLogger(EnvPort.class);

  private final Property property;
  private final Port orDefault;

  EnvPort(final Property property, final Port orDefault) {
    this.property = property;
    this.orDefault = orDefault;
  }

  @Override
  public final int get() {
    try {
      return Integer.parseInt(property.get());
    } catch (NumberFormatException nfe) {
      log.warn("Provided port is not a number.");
      return orDefault.get();
    }
  }
}
