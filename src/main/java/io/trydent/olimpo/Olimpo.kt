package io.trydent.olimpo

import io.trydent.olimpo.http.HttpExchange.Companion.staticContent
import io.trydent.olimpo.http.HttpRequest.Companion.switch
import io.trydent.olimpo.http.HttpRoute.Companion.webroot
import io.trydent.olimpo.http.HttpServer.Companion.httpServer
import io.trydent.olimpo.io.Port.Companion.envPort
import io.trydent.olimpo.vertx.deploy
import io.vertx.core.Vertx.vertx

fun main() = vertx().deploy({
    httpServer(
      switch(
        webroot(
          path = "/*",
          exchange = staticContent(
            folder = "webroot"
          )
        )
      )
    ).invoke(envPort(name = "PORT", default = 8080))
  }
)
