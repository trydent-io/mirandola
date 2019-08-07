package io.trydent.olimpo.http;

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
    httpServer.listen(portOrDie(8141));

    given()
      .port(8141)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML);
  }
}
