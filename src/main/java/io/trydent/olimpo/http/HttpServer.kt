package io.trydent.olimpo.http

import io.trydent.olimpo.io.Port
import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx
import org.slf4j.LoggerFactory.getLogger

interface HttpServer : (Port) -> Unit {
  companion object {
    fun httpServer(request: HttpRequest): HttpServer = HttpRequestServer(vertx(), request)
  }
}

internal class HttpRequestServer(private val vertx: Vertx, private val request: HttpRequest) : HttpServer {
  private val log = getLogger(javaClass)

  override fun invoke(port: Port) {
    vertx
      .createHttpServer()
      .requestHandler(request())
      .listen(port()) {
        when {
          it.succeeded() -> log.info("Http Server started on port $port.")
          it.failed() -> log.error("Http Server failed to start on port $port.")
        }
      }
  }
}
