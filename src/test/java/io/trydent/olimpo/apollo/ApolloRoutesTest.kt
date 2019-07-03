package io.trydent.olimpo.apollo

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.media.json
import io.trydent.olimpo.test.anyString
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class ApolloRoutesTest {
  private val httpServer = OlimpoHttpServer(
    AddReadingRoute(
      path = "/addReading",
      request = AddReadingRequest()
    )
  )

  @Test
  internal fun `should add reading`(vertx: Vertx) {
    httpServer(vertx, 8090)

    given()
      .port(8090)
      .body(
        json(
          "extracted" to 35489.0,
          "entered" to 342345.0,
          "produced" to 435345.0
        )
      )
      .contentType(JSON)
      .post("/addReading")
    .then()
      .statusCode(200)
      .contentType(JSON)
      .body("commandId", anyString())
  }
}
