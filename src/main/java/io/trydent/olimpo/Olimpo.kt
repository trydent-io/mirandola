package io.trydent.olimpo

import io.trydent.olimpo.http.HelloExchange
import io.trydent.olimpo.http.HelloRoute
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.WebrootExchange
import io.trydent.olimpo.http.WebrootRoute
import io.trydent.olimpo.vertx.deploy
import io.vertx.core.Vertx.vertx
import java.lang.Integer.parseInt
import java.lang.System.getenv

fun main() = vertx().deploy(
  { vertx ->
    OlimpoHttpServer(
      vertx,
      WebrootRoute(
        path = "/*",
        exchange = WebrootExchange(
          resources = "webroot"
        )
      ),
      HelloRoute(
        path = "/api/hello",
        exchange = HelloExchange(
          dest = "world"
        )
      )
    ).invoke(getenv("PORT")?.let { parseInt(it) } ?: 8080)
  }
)
