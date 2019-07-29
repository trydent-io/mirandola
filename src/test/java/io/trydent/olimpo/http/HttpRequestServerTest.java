package io.trydent.olimpo.http;

import io.trydent.olimpo.io.Port;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.HTML;
import static io.trydent.olimpo.io.Port.portOrDie;

@ExtendWith(VertxExtension.class)
class HttpRequestServerTest {
  private final Port port = portOrDie(8090);
  private final HttpServer httpServer;

  HttpRequestServerTest(Vertx vertx) {
    this.httpServer = new HttpRequestServer(
      vertx,
      new HttpRouteSwitch(
        vertx,
        new HttpRoute[]{
          new WebrootRoute(
            "/*",
            new StaticContent("webroot")
          )
        }
      )
    );
  }

  @Test
  @DisplayName("should start Server")
  void shouldStartServer() {
    httpServer.accept(port);

    given()
      .port(8090)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML);
  }
}
