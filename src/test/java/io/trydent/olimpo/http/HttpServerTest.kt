package io.trydent.olimpo.http

import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.trydent.olimpo.io.Port
import io.trydent.olimpo.test.isPresent
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest(vertx: Vertx) {
  private val port: Port = mockk()
  private val httpServer = HttpServer(
    HttpSwitch(
      WebrootRoute(
        path = "/*",
        exchange = WebrootFolder("webroot")
      ),
      HelloRoute(
        path = "/api/hello",
        exchange = HelloExchange("world")
      )
    )
  )

  @Test
  internal fun `should start a server`() {
    every { port() } returns 8090

    httpServer(port)

    given()
      .port(8090)
      .get()
      .then()
      .statusCode(200)
      .contentType(HTML)
  }

  @Test
  internal fun `should get a json message`() {
    every { port() } returns 8090

    httpServer(port)

    given()
      .port(8090)
      .get("/api/hello")
      .then()
      .statusCode(200)
      .body("message", "Hello world!".isPresent)
  }
}
