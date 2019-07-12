package io.trydent.olimpo.http

import io.trydent.olimpo.io.Port
import io.trydent.olimpo.sys.Property
import io.vertx.core.Vertx
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.parseInt

class EnvironmentPort(private val property: Property) : Port {
  override fun invoke(default: Int): Int = try {
    parseInt(property())
  } catch (nfe: NumberFormatException) {
    default
  }
}

interface HttpSocket : (Port) -> Unit

class HttpServer(private val vertx: Vertx, private val request: HttpRequest) : HttpSocket {
  private val log = getLogger(javaClass)

  override fun invoke(port: Port) {
    vertx
      .createHttpServer()
      .requestHandler(request())
      .listen(port(8080)) {
        when {
          it.succeeded() -> log.info("Http Server started on port $port.")
          it.failed() -> log.error("Http Server failed to start on port $port.")
        }
      }
  }
}
