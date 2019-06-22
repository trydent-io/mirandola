package io.trydent.olimpo.sys

import io.vertx.core.Verticle
import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface Deploy : (Verticle) -> Unit

class VerticleDeploy(private val vertx: Vertx) : Deploy {
  private val log: Logger = getLogger(javaClass)

  override fun invoke(verticle: Verticle) {
    vertx.deployVerticle(verticle) {
      when {
        it.succeeded() -> log.info("Verticle ${verticle.javaClass.name} deployed.")
        it.failed() -> log.error("Verticle ${verticle.javaClass.name} not deployed: ${it.cause().message}.")
      }
    }
  }
}
