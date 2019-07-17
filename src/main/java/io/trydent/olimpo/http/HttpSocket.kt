package io.trydent.olimpo.http

import io.trydent.olimpo.VertxContainer.vertx
import io.trydent.olimpo.io.Port
import org.slf4j.LoggerFactory.getLogger

interface HttpSocket : (Port) -> Unit {
}

class HttpServer(private val request: HttpRequest) : HttpSocket {
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
