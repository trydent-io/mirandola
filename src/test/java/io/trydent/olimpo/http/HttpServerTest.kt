package io.trydent.olimpo.http

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.vertx.core.Vertx.vertx
import io.vertx.junit5.VertxExtension
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  private val vertx = vertx()
  private val httpServer: HttpServer = OlimpoHttpServer(
    vertx,
    WebrootResource(
      path = "/*",
      request = WebrootRequest(
        resources = "webroot"
      )
    ),
    HelloResource(
      path = "/api/hello",
      request = HelloRequest(dest = "world")
    )
  )

  @Test
  internal fun `should start a server`() {
    val started = httpServer(8090)

    given()
      .port(8090)
      .get()
    .then()
      .statusCode(200)
      .contentType(HTML)

    started.close()
  }

  @Test
  internal fun `should get a json message`() {
    val started = httpServer(8090)

    given()
      .port(8090)
      .get("/api/hello")
    .then()
      .statusCode(200)
      .body("message", "Hello world!".isPresent)

    started.close()
  }
}

private val String.isPresent get() = `is`(this)
