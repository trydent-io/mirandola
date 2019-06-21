package io.trydent.olimpo

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.trydent.olimpo.http.HelloworldResource
import io.trydent.olimpo.http.HttpServer
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.WebrootResource
import io.vertx.core.Vertx
import io.vertx.ext.web.Router.router
import io.vertx.junit5.VertxExtension
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory.getLogger

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  private val log = getLogger(javaClass)

  private lateinit var httpServer: HttpServer

  @BeforeEach
  internal fun setUp(vertx: Vertx) {
    httpServer = OlimpoHttpServer(
      vertx,
      router(vertx),
      WebrootResource(path = "/*"),
      HelloworldResource(path = "/api/hello")
    )
  }

  @AfterEach
  internal fun tearDown(vertx: Vertx) {
    vertx.close {
      when {
        it.succeeded() -> log.info("Vertx closed.")
        it.failed() -> log.error("Vertx wasn't able to close itself.")
      }
    }
  }

  @Test
  internal fun `should start a server`() {
    httpServer(8090)

    given()
      .port(8090)
      .get()
      .then()
      .statusCode(200)
      .contentType(HTML)
  }

  @Test
  internal fun `should get a json message`() {
    httpServer(8090)

    given()
      .port(8090)
      .get("/api/hello")
      .then()
      .statusCode(200)
      .body("message", "Hello world.".isPresent)
  }
}

private val String.isPresent get() = `is`(this)
