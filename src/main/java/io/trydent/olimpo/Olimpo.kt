package io.trydent.olimpo

import io.trydent.olimpo.http.HelloExchange
import io.trydent.olimpo.http.HelloRoute
import io.trydent.olimpo.http.HttpServerVerticle
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.WebrootExchange
import io.trydent.olimpo.http.WebrootRoute
import io.trydent.olimpo.sys.Deployment
import io.trydent.olimpo.sys.VerticleDeployment
import io.vertx.core.Vertx.vertx

private val deployment: Deployment =
  VerticleDeployment(
    HttpServerVerticle(
      OlimpoHttpServer(
        WebrootRoute(
          path = "/*",
          request = WebrootExchange(
            resources = "webroot"
          )
        ),
        HelloRoute(
          path = "/api/hello",
          request = HelloExchange(
            dest = "world"
          )
        )
      )
    )
  )

fun main() = deployment(vertx())
