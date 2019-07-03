package io.trydent.olimpo

import io.trydent.olimpo.http.HelloExchange
import io.trydent.olimpo.http.HelloResource
import io.trydent.olimpo.http.HttpServerVerticle
import io.trydent.olimpo.http.OlimpoHttpServer
import io.trydent.olimpo.http.WebrootExchange
import io.trydent.olimpo.http.WebrootResource
import io.trydent.olimpo.sys.Deployment
import io.trydent.olimpo.sys.VerticleDeployment
import io.vertx.core.Vertx.vertx

private val deployment: Deployment =
  VerticleDeployment(
    HttpServerVerticle(
      OlimpoHttpServer(
        WebrootResource(
          path = "/*",
          request = WebrootExchange(
            resources = "webroot"
          )
        ),
        HelloResource(
          path = "/api/hello",
          request = HelloExchange(
            dest = "world"
          )
        )
      )
    )
  )

fun main() = deployment(vertx())
