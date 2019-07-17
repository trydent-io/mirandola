package io.trydent.olimpo

import io.trydent.olimpo.http.HttpServer
import io.trydent.olimpo.http.HttpSwitch
import io.trydent.olimpo.http.WebrootFolder
import io.trydent.olimpo.http.WebrootRoute
import io.trydent.olimpo.io.Port
import io.trydent.olimpo.vertx.deploy
import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx

object VertxContainer {
  val vertx: Vertx by lazy { vertx() }
}

fun main() = vertx().deploy({
    HttpServer(
      HttpSwitch(
        WebrootRoute(
          path = "/*",
          exchange = WebrootFolder(
            folder = "webroot"
          )
        )
      )
    ).invoke(Port.fromEnvVar(name = "PORT", default = 8080))
  }
)
