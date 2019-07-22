package io.trydent.olimpo.http

import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import io.restassured.matcher.ResponseAwareMatcher
import io.trydent.olimpo.apollo.Id.Companion.id
import io.trydent.olimpo.apollo.Id.Companion.uuid
import io.trydent.olimpo.dispatch.AsyncCommand.Companion.busCommand
import io.trydent.olimpo.http.HttpExchange.Companion.actionExecution
import io.trydent.olimpo.http.HttpRequest.Companion.single
import io.trydent.olimpo.http.HttpRoute.Companion.action
import io.trydent.olimpo.http.HttpServer.Companion.httpServer
import io.trydent.olimpo.io.Port.Companion.portOrDie
import io.trydent.olimpo.test.isPresent
import io.trydent.olimpo.test.json
import io.trydent.olimpo.vertx.Json
import io.trydent.olimpo.vertx.json
import io.vertx.core.Vertx
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.hamcrest.text.MatchesPattern
import org.hamcrest.text.MatchesPattern.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

@ExtendWith(VertxExtension::class)
internal class HttpActionIT(vertx: Vertx) {
  val bus = vertx.eventBus()
  private val httpServer = httpServer(
    vertx,
    request = single(
      vertx,
      route = action(
        path = "/api/apollo/:action",
        exchange = actionExecution(
          busCommand(bus)
        )
      )
    )
  )

  @Test
  @Timeout(4, timeUnit = SECONDS)
  internal fun `should execute any-action`(test: VertxTestContext) {
    httpServer(portOrDie(8090))

    bus.localConsumer<Json>("any-action-command") {
      it.reply(json(
        "id" to id("abd94004-6f46-4a12-b28d-ec9be66a6089").invoke()
      ))
      test.completeNow()
    }

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
      .body("actionId", "abd94004-6f46-4a12-b28d-ec9be66a6089".isPresent)
      .body("actionId", matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
  }
}
