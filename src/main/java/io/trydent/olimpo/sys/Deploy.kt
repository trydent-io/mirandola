package io.trydent.olimpo.sys

import io.trydent.olimpo.http.HttpServerVerticle
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface Deploy : () -> Unit

class VerticleDeploy(private val vertx: Vertx, private vararg val verticles: HttpServerVerticle) : Deploy {
  private val log: Logger = getLogger(javaClass)

  override fun invoke() {
    verticles.forEach { verticle ->
      vertx.deployVerticle(verticle) {
        when {
          it.succeeded() -> log.info("Verticle ${verticle.javaClass.name} deployed.")
          it.failed() -> log.error("Verticle ${verticle.javaClass.name} not deployed: ${it.cause().message}.")
        }
      }
    }
  }
}
