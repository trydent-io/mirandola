package io.trydent.olimpo.sys

import io.trydent.olimpo.http.HttpServerVerticle
import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface Deployment : (Vertx) -> Unit

class VerticleDeployment(private vararg val verticles: HttpServerVerticle) : Deployment {
  private val log: Logger = getLogger(javaClass)

  override fun invoke(vertx: Vertx) {
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
