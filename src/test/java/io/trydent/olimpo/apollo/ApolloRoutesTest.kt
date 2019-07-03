package io.trydent.olimpo.apollo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.trydent.olimpo.apollo.ApolloCommand.AddReading
import io.trydent.olimpo.bus.CommandBus
import io.trydent.olimpo.http.HttpServer
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.media.json
import io.trydent.olimpo.test.isPresent
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class ApolloRoutesTest {
  private val commands: CommandBus = mockk()
  private lateinit var httpServer: HttpServer

  @BeforeEach
  internal fun setUp(vertx: Vertx) {
    httpServer = OlimpoHttpServer(
      vertx,
      AddReadingRoute(
        path = "/add-reading",
        exchange = AddReadingExchange(
          bus = commands
        )
      )
    )
  }

  @Test
  internal fun `should add reading`(vertx: Vertx) {
    val json = json(
      "extracted" to 35489.0,
      "entered" to 342345.0,
      "produced" to 435345.0
    )

    every { commands.send(command = AddReading, params = any()) } returns "commandId"
    httpServer(8090)

    given()
      .port(8090)
      .body(json)
      .accept(JSON)
      .contentType(JSON)
      .post("/add-reading")
      .then()
      .statusCode(200)
      .contentType(JSON)
      .body("commandId", "commandId".isPresent)

    verify { commands.send(command = AddReading, params = any()) }
  }
}
