package io.trydent.mirandola

import io.vertx.core.AbstractVerticle
import io.vertx.core.Verticle
import io.vertx.core.Vertx.vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.*

interface Json {
  val asString: String
  val asBuffer: Buffer
}

data class Message(private val message: String) : Json {
  override val asString get() = JsonObject().put("message", message).toString()

  override val asBuffer: Buffer get() = JsonObject().put("message", message).toBuffer()
}

class HttpServerVerticle : AbstractVerticle(), Verticle {
  private val log = getLogger(javaClass)

  override fun start() {
    val httpServer = vertx.createHttpServer()
    val router = router(vertx)

    router.get("/:message")
      .produces("application/json")
      .handler {
        log.info("Received request on root.")
        it.response()
          .putHeader("content-type", "application/json")
          .end(Message("Hello ${it.request().params["message"] ?: "world"}.").asBuffer)
      }

    val port = parseInt(System.getenv("PORT") ?: "8080")

    httpServer
      .requestHandler(router)
      .listen(port, "0.0.0.0") {
        log.info("HttpServer listens on port: $port.")
      }
  }
}

private val HttpServerRequest.params get() = this.params()

private val log = getLogger("MAIN")

fun main() {
  val vertx = vertx()
  vertx.deployVerticle(HttpServerVerticle()) {
    it
      .takeIf { result -> result.succeeded() }
      .also { log.info("HttpServer verticle deployed.") }
      ?: log.error("HttpServer verticle met a problem: ${it.cause().message}")
  }
}
