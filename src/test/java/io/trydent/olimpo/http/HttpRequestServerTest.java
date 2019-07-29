package io.trydent.olimpo.http

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.trydent.olimpo.io.Port
import io.trydent.olimpo.io.Port.Companion.port
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpRequestServerTest(vertx: Vertx) {
  private val port: Port = port(8090)!!
  private val httpServer = HttpRequestServer(
    vertx,
    HttpRouteSwitch(
      vertx,
      WebrootRoute(
        path = "/*",
        exchange = StaticContent("webroot")
      )
    )
  )

  @Test
  internal fun `should start a server`() {
    httpServer(port)

    given()
      .port(8090)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML)
  }
}
