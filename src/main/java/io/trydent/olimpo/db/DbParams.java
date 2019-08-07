package io.trydent.olimpo.db;

import io.trydent.olimpo.sys.Property;
import io.trydent.olimpo.vertx.json.Json;
import io.vertx.core.json.JsonObject;
import org.postgresql.Driver;

import java.net.URI;
import java.net.URISyntaxException;

import static io.trydent.olimpo.sys.Property.envVar;
import static java.lang.String.format;

public interface DbParams extends Json {
  static DbParams postgresqlParams(final Property property) {
    return new PostgresqlParams(
      property,
      Driver.class,
      "jdbc:postgresql://%s:%d%s"
    );
  }
  static DbParams envPostgresql(final String envVar) {
    return postgresqlParams(envVar(envVar));
  }
}

final class PostgresqlParams implements DbParams {
  private static final String REGEX = "(postgres)://([A-Za-z0-9])\\w+:([A-Za-z0-9])\\w+@([A-Za-z0-9-.]+):([0-9]{4})/([A-Za-z0-9])\\w+";

  private final Property property;
  private final Class<Driver> driverClass;
  private final String template;

  PostgresqlParams(final Property property, final Class<Driver> driverClass, final String template) {
    this.property = property;
    this.driverClass = driverClass;
    this.template = template;
  }

  @Override
  public final JsonObject get() {
    try {
      final var url = property.get();
      if (url.matches(REGEX)) {
        final var uri = new URI(url);
        final var userInfo = uri.getUserInfo().split(":");

        return new JsonObject()
          .put("url", format(template, uri.getHost(), uri.getPort(), uri.getPath()))
          .put("user", userInfo[0])
          .put("password", userInfo[1])
          .put("driver_class", driverClass.getName());
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }
}
