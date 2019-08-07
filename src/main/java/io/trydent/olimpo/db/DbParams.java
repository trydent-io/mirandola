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
  static DbParams postgresParams(final Property property) {
    return new JdbcParams<>(
      property,
      Driver.class,
      "jdbc:postgresql://%s:%d%s",
      "(postgres)://([A-Za-z0-9])\\w+:([A-Za-z0-9])\\w+@([A-Za-z0-9-.]+):([0-9]{4})/([A-Za-z0-9])\\w+"
    );
  }
  static DbParams envPostgres(final String envVar) {
    return postgresParams(envVar(envVar));
  }

  static <D extends java.sql.Driver> DbParams jdbcUrl(final String jdbcUrl, final Class<D> driverClass) {
    return () -> new JsonObject()
      .put("url", jdbcUrl)
      .put("driver_class", driverClass.getName());
  }
}

final class JdbcParams<D extends java.sql.Driver> implements DbParams {
  private final Property property;
  private final Class<D> driverClass;
  private final String template;
  private final String regex;

  JdbcParams(final Property property, final Class<D> driverClass, final String template, final String regex) {
    this.property = property;
    this.driverClass = driverClass;
    this.template = template;
    this.regex = regex;
  }

  @Override
  public final JsonObject get() {
    try {
      if (!property.get().matches(regex)) return null;

      final var uri = new URI(property.get());
      final var userInfo = uri.getUserInfo().split(":");

      return new JsonObject()
        .put("url", format(template, uri.getHost(), uri.getPort(), uri.getPath()))
        .put("user", userInfo[0])
        .put("password", userInfo[1])
        .put("driver_class", driverClass.getName());
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    }
  }
}
