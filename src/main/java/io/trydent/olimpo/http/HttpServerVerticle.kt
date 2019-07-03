package io.trydent.olimpo.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.Verticle
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.parseInt
import java.lang.System.getenv

class HttpServerVerticle(private val httpServer: HttpServer) : AbstractVerticle(), Verticle {
  private val log = getLogger(javaClass)

  private val port = getenv("PORT")?.let { parseInt(it) } ?: 8080

  override fun start() {
    httpServer(vertx, port)
    log.info("HttpServer verticle started.")
  }
}
