package io.trydent.olimpo.http

import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface HttpServer : (Int) -> Unit

class OlimpoHttpServer(private val vertx: Vertx, private val webroot: HttpResource) : HttpServer {
  private val log: Logger by lazy { getLogger(javaClass) }

  override fun invoke(port: Int) {
    vertx.createHttpServer()
      .requestHandler(webroot())
      .listen(port) {
        when {
          it.succeeded() -> log.info("Olimpo Http Server started on port $port.")
          it.failed() -> log.error("Olimpo Http Server failted to start on port $port.")
        }
      }
  }
}
