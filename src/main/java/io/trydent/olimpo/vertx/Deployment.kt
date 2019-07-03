package io.trydent.olimpo.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import org.slf4j.LoggerFactory.getLogger
import java.util.UUID.randomUUID

private val log = getLogger("Verticle-${randomUUID()}")

fun Vertx.deploy(verticle: (Vertx) -> Unit) = this.apply {
  deployVerticle(
    object : AbstractVerticle() {
      override fun start() = verticle(vertx)
    }
  ) {
    when {
      it.succeeded() -> log.info("Verticle ${verticle.javaClass.name} deployed.")
      it.failed() -> log.error("Verticle ${verticle.javaClass.name} not deployed: ${it.cause().message}.")
    }
  }
}
