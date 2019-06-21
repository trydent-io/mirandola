package io.trydent.olimpo

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.HTML
import io.trydent.olimpo.http.HttpServer
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.WebrootResource
import io.vertx.core.Vertx
import io.vertx.ext.web.Router.router
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  private lateinit var httpServer: HttpServer

  @BeforeEach
  internal fun setUp(vertx: Vertx) {
    httpServer = OlimpoHttpServer(
      vertx,
      WebrootResource(
        path = "/*",
        router = router(vertx)
      )
    )
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
}
