package io.trydent.olimpo.http;

import io.vertx.ext.web.Router;

import java.util.function.UnaryOperator;

import static io.trydent.olimpo.http.HttpHeader.Value.ApplicationJson;

public interface HttpRoute extends UnaryOperator<Router> {
  static HttpRoute webroot(String path, HttpExchange exchange) {
    return new WebrootRoute(path, exchange);
  }

  static HttpRoute action(String path, HttpExchange exchange) {
    return new ActionRoute(path, exchange);
  }
}

final class WebrootRoute implements HttpRoute {
  private final String path;
  private final HttpExchange exchange;

  WebrootRoute(final String path, final HttpExchange exchange) {
    this.path = path;
    this.exchange = exchange;
  }

  @Override
  public final Router apply(final Router router) {
    router.get(path).handler(exchange.get());
    return router;
  }
}

final class ActionRoute implements HttpRoute {
  private final String path;
  private final HttpExchange exchange;

  ActionRoute(final String path, final HttpExchange exchange) {
    this.path = path;
    this.exchange = exchange;
  }

  @Override
  public final Router apply(final Router router) {
    router.post(path)
      .consumes(ApplicationJson)
      .produces(ApplicationJson)
      .handler(exchange.get());
    return router;
  }
}
