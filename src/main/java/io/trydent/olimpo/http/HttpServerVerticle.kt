package io.trydent.olimpo.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.Verticle
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.parseInt
import java.lang.System.getenv

class HttpServerVerticle : AbstractVerticle(), Verticle {
  private val log = getLogger(javaClass)

  private val httpServer: HttpServer by lazy {
    OlimpoHttpServer(
      vertx,
      WebrootResource(path = "/*", request = WebrootRequest(resources = "webroot")),
      HelloResource(path = "/api/hello", request = HelloRequest(dest = "world"))
    )
  }
  private val port = getenv("PORT")?.let { parseInt(it) } ?: 8080

  override fun start() {
    httpServer(port)
  }
}
