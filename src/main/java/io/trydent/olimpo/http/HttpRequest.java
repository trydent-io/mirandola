package io.trydent.olimpo.http;

import io.trydent.olimpo.type.Type;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import org.slf4j.Logger;

import java.util.Arrays;

import static io.vertx.ext.web.Router.router;
import static org.slf4j.LoggerFactory.getLogger;

public interface HttpRequest extends Type.As<Handler<HttpServerRequest>> {
    static HttpRequest routeSwitch(final Vertx vertx, final HttpRoute... routes) {
      return new HttpRouteSwitch(vertx, Arrays.copyOf(routes, routes.length));
    }

    static HttpRequest single(Vertx vertx, HttpRoute route) {
      return new HttpSingle(vertx, route);
    }
}

final class HttpRouteSwitch implements HttpRequest {
  private static final Logger log = getLogger(HttpRouteSwitch.class);

  private final Vertx vertx;
  private final HttpRoute[] routes;

  HttpRouteSwitch(final Vertx vertx, final HttpRoute[] routes) {
    this.vertx = vertx;
    this.routes = routes;
  }

  @Override
  public final Handler<HttpServerRequest> get() {
    final var router = router(vertx);
    for (var route : routes) route.apply(router);
    return router;
  }
}

final class HttpSingle implements HttpRequest {
  private static final Logger log = getLogger(HttpSingle.class);

  private final Vertx vertx;
  private final HttpRoute route;

  HttpSingle(Vertx vertx, HttpRoute route) {
    this.vertx = vertx;
    this.route = route;
  }

  @Override
  public final Handler<HttpServerRequest> get() {
    return route.apply(router(vertx));
  }
}
