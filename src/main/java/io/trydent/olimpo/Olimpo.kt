package io.trydent.olimpo

import io.trydent.olimpo.http.EnvironmentPort
import io.trydent.olimpo.http.HelloExchange
import io.trydent.olimpo.http.HelloRoute
import io.trydent.olimpo.http.HttpServer
import io.trydent.olimpo.http.HttpSwitch
import io.trydent.olimpo.http.WebrootFolder
import io.trydent.olimpo.http.WebrootRoute
import io.trydent.olimpo.sys.EnvironmentVariable
import io.trydent.olimpo.vertx.deploy
import io.vertx.core.Vertx.vertx

fun main() = vertx().deploy(
  { vertx ->
    HttpServer(
      vertx,
      HttpSwitch(
        vertx,
        WebrootRoute(
          path = "/*",
          exchange = WebrootFolder(
            folder = "webroot"
          )
        ),
        HelloRoute(
          path = "/api/hello",
          exchange = HelloExchange(
            dest = "world"
          )
        )
      )
    )(
      EnvironmentPort(
        EnvironmentVariable("PORT")
      )
    )
  }
)
