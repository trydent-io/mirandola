package io.trydent.olimpo.http

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.restassured.http.ContentType.JSON
import io.trydent.olimpo.http.media.json
import io.trydent.olimpo.test.anyString
import io.trydent.olimpo.test.isPresent
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.hamcrest.CoreMatchers.any
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  private val vertx = Vertx.vertx()
  private val httpServer: HttpServer = OlimpoHttpServer(
    WebrootResource(
      "/*",
      WebrootRequest("webroot")
    ),
    HelloResource(
      "/api/hello",
      HelloRequest("world")
    )
  )

  @Test
  internal fun `should start a server`() {
    httpServer(vertx, 8090)

    given()
      .port(8090)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML)
  }

  @Test
  internal fun `should get a json message`() {
    httpServer(vertx, 8090)

    given()
      .port(8090)
      .get("/api/hello")
    .then()
      .statusCode(200)
      .body("message", "Hello world!".isPresent)
  }
}
