package io.trydent.olimpo.apollo

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.media.json
import io.trydent.olimpo.test.anyString
import io.vertx.core.Vertx
import org.junit.jupiter.api.Test

class ApolloRoutesTest {
  private val vertx = Vertx.vertx()
  private val httpServer = OlimpoHttpServer(
    AddReadingRoute(
      path = "/sun/addReading",
      request = AddReadingRequest()
    )
  )

  @Test
  internal fun `should add reading`() {
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
      .post("/sun/addReading")
    .then()
      .statusCode(200)
      .contentType(JSON)
      .body("commandId", anyString())
  }
}
