package io.trydent.mirandola

import io.restassured.RestAssured.given
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class WebServerImplTest {
  @Test
  internal fun `should start a server`(vertx: Vertx) {
    val server = WebServerImpl(
      vertx.createHttpServer(),
      "localhost",
      8080
    )

    server()
    given()
      .basePath("localhost")
      .port(8090)
    .then()
      .statusCode(200)
  }
}
