package io.trydent.olimpo.sys

import io.vertx.core.Verticle
import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface Deployment : (Verticle) -> Unit

class VerticleDeployment(private val vertx: Vertx) : Deployment {
  private val log: Logger by lazy { getLogger(javaClass) }

  override fun invoke(verticle: Verticle) {
    vertx.deployVerticle(verticle) {
      when {
        it.succeeded() -> log.info("Verticle ${verticle.javaClass.name} deployed.")
        it.failed() -> log.error("Verticle ${verticle.javaClass.name} not deployed: ${it.cause().message}.")
      }
    }
  }
}
