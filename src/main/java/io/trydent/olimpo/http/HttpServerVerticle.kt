package io.trydent.olimpo.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.Verticle
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.parseInt
import java.lang.System.getenv

class HttpServerVerticle : AbstractVerticle(), Verticle {
  private val log = getLogger(javaClass)

  private val httpServer: HttpServer by lazy {
    OlimpoHttpServer(
      vertx,
      router(vertx),
      WebrootResource(path = "/*"),
      HelloworldResource(path = "/api/hello")
    )
  }
  private val port = getenv("PORT")?.let { parseInt(it) } ?: 8080

  override fun start() = httpServer(port)
}
