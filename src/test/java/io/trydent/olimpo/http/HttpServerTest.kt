package io.trydent.olimpo.http

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.trydent.olimpo.test.isPresent
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  private val httpServer: HttpServer = OlimpoHttpServer(
    WebrootResource(
      "/*",
      WebrootExchange("webroot")
    ),
    HelloResource(
      "/api/hello",
      HelloExchange("world")
    )
  )

  @Test
  internal fun `should start a server`(vertx: Vertx) {
    httpServer(vertx, 8090)

    given()
      .port(8090)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML)
  }

  @Test
  internal fun `should get a json message`(vertx: Vertx) {
    httpServer(vertx, 8090)

    given()
      .port(8090)
      .get("/api/hello")
    .then()
      .statusCode(200)
      .body("message", "Hello world!".isPresent)
  }
}
