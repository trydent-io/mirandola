package io.trydent.olimpo.apollo

import io.trydent.olimpo.dispatch.BusCommand
import io.trydent.olimpo.http.HttpRequestServer
import io.trydent.olimpo.http.HttpSwitch
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class AddReadingRouteTest(vertx: Vertx) {
  private val httpServer = HttpRequestServer(
    vertx,
    HttpSwitch(
      vertx,
      AddReadingRoute(
        path = "/apollo/:command",
        exchange = AddReadingExchange(
          command = BusCommand(
            vertx.eventBus()
          )
        )
      )
    )
  )

  @Test
  internal fun `should route any command for apollo`() {

  }
}
