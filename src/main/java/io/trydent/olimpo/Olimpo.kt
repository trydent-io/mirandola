package io.trydent.olimpo

import io.trydent.olimpo.action.Action.Companion.busCommand
import io.trydent.olimpo.apollo.ApolloCommand.Companion.addReading
import io.trydent.olimpo.bus.Bus.Companion.commandBus
import io.trydent.olimpo.bus.Process.Companion.commandProcess
import io.trydent.olimpo.http.HttpExchange.Companion.actionSwitch
import io.trydent.olimpo.http.HttpExchange.Companion.staticContent
import io.trydent.olimpo.http.HttpRequest.Companion.routeSwitch
import io.trydent.olimpo.http.HttpRoute.Companion.action
import io.trydent.olimpo.http.HttpRoute.Companion.webroot
import io.trydent.olimpo.http.HttpServer.Companion.httpServer
import io.trydent.olimpo.io.Port.Companion.envPort
import io.trydent.olimpo.sys.Id.Companion.uuid
import io.trydent.olimpo.vertx.deploy
import io.vertx.core.Vertx.vertx

fun main() = vertx().deploy({ vertx ->
  httpServer(
    vertx,
    routeSwitch(
      vertx,
      webroot("/*",
        staticContent(
          "webroot"
        )
      ),
      action("/apollo/:action",
        actionSwitch(
          busCommand(vertx.eventBus())
        )
      )
    )
  )(
    envPort(name = "PORT", default = 8095)
  )
}, { vertx ->
  commandBus(
    commandProcess(uuid(), vertx.eventBus()),
    addReading()
  )()
})
