package io.trydent.mirandola

import io.restassured.RestAssured.given
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class HttpServerTest {
  @Test
  internal fun `should start`(vertx: Vertx) {
    val httpServer = HttpServerImpl(vertx.createHttpServer())

    httpServer.listen(host = "localhost", port = 8090)

    given()
      .port(8090)
      .then()
      .statusCode(200)
  }
}
