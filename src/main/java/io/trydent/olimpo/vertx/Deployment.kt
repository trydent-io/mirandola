package io.trydent.olimpo.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import java.util.UUID.randomUUID

private val log = getLogger("Verticle-${randomUUID()}")

fun Vertx.deploy(vararg services: (Vertx) -> Unit) = services.forEach { service ->
  deployVerticle(
    object : AbstractVerticle() {
      override fun start() = service(vertx)
    }
  ) {
    when {
      it.succeeded() -> log.info("Verticle ${service.javaClass.name} deployed.")
      it.failed() -> log.error("Verticle ${service.javaClass.name} not deployed: ${it.cause().message}.")
    }
  }
}
