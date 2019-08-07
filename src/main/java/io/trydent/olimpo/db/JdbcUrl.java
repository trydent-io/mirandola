package io.trydent.olimpo.db;

import io.trydent.olimpo.sys.Property;
import io.trydent.olimpo.type.Type;

import java.net.URI;
import java.net.URISyntaxException;

import static io.trydent.olimpo.sys.Property.envVar;

public interface JdbcUrl extends Type.AsString {
  static JdbcUrl envJdbcUrl(final Property property) {
    return new EnvJdbcUrl(property);
  }
  static JdbcUrl envJdbcUrl(final String envVar) {
    return envJdbcUrl(envVar(envVar));
  }
}

final class EnvJdbcUrl implements JdbcUrl {
  private final Property property;

  EnvJdbcUrl(final Property property) {
    this.property = property;
  }

  @Override
  public final String get() {
    try {
      final var env = property.get();
      final var uri = new URI(env);
      final var username = uri.getUserInfo().split(":")[0];
      final var password = uri.getUserInfo().split(":")[1];
      return "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?user=" + username + "&password=" + password;
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
