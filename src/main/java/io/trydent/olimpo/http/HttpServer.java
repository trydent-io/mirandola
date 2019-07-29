package io.trydent.olimpo.http;

import io.trydent.olimpo.io.Port;
import io.vertx.core.Vertx;
import org.slf4j.Logger;

import java.util.function.Consumer;

import static io.trydent.olimpo.type.When.when;
import static org.slf4j.LoggerFactory.getLogger;

public interface HttpServer extends Consumer<Port> {
  static HttpServer httpServer(Vertx vertx, HttpRequest request) {
    return new HttpRequestServer(vertx, request);
  }
}

final class HttpRequestServer implements HttpServer {
  private static final Logger log = getLogger(HttpRequestServer.class);

  private final Vertx vertx;
  private final HttpRequest request;

  HttpRequestServer(Vertx vertx, HttpRequest request) {
    this.vertx = vertx;
    this.request = request;
  }

  @Override
  public final void accept(Port port) {
    vertx
      .createHttpServer()
      .requestHandler(request.get())
      .listen(port.get(), async -> when(async.succeeded(),
        () -> log.info("Http Server started on port {}.", port),
        () -> log.error("Http Server failed to start on port {}.", port)
      ));
  }
}
