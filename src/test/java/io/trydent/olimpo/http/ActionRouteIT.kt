package io.trydent.olimpo.http

import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.trydent.olimpo.apollo.Id.Companion.uuid
import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.http.HttpExchange.Companion.actionExecution
import io.trydent.olimpo.http.HttpRequest.Companion.single
import io.trydent.olimpo.http.HttpRoute.Companion.action
import io.trydent.olimpo.http.HttpServer.Companion.httpServer
import io.trydent.olimpo.io.Port.Companion.portOrDie
import io.trydent.olimpo.test.json
import io.trydent.olimpo.vertx.json
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class ActionRouteIT(vertx: Vertx) {
  private val command: Command = mockk()

  private val httpServer = httpServer(
    vertx,
    request = single(
      vertx,
      route = action(
        path = "/api/apollo/:action",
        exchange = actionExecution(command)
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
    } returns uuid()

    httpServer(portOrDie(8090))

    given()
      .port(8090)
      .contentType(JSON)
      .json(
        "produced" to 3000.0,
        "consumed" to 2000.0,
        "putIn" to 1000.0
      )
      .post("/api/apollo/{action}", "any-action")
      .then()
      .statusCode(200)
      .contentType(JSON)
      .body("actionId", notNullValue())
  }
}
