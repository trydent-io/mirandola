package io.trydent.olimpo.http

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.trydent.olimpo.apollo.Id
import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.dispatch.CommandId
import io.trydent.olimpo.io.Port
import io.trydent.olimpo.test.json
import io.trydent.olimpo.vertx.json
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import io.vertx.junit5.VertxExtension
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class ActionExecutionIT(vertx: Vertx) {
  private val command: Command = mockk()

  private val httpServer = HttpServer.httpServer(
    vertx,
    request = HttpRequest.single(
      vertx,
      route = HttpRoute.action(
        path = "/api/apollo/:action",
        exchange = HttpExchange.actionExecution(command)
      )
    )
  )

  @Test
  internal fun `should execute any-action`() {
    every {
      command(
        "any-action",
        json(
          "produced" to 3000.0,
          "consumed" to 2000.0,
          "putIn" to 1000.0
        )
      )
    } returns Id.uuid()

    httpServer(Port.portOrDie(8090))

    RestAssured.given()
      .port(8090)
      .contentType(ContentType.JSON)
      .json(
        "produced" to 3000.0,
        "consumed" to 2000.0,
        "putIn" to 1000.0
      )
      .post("/api/apollo/{action}", "any-action")
    .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("actionId", CoreMatchers.notNullValue())
  }
}
