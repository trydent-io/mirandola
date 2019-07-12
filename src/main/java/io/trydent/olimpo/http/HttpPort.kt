package io.trydent.olimpo.http

import io.trydent.olimpo.io.Port
import io.trydent.olimpo.sys.Property
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory.getLogger
import java.lang.Integer.parseInt

class EnvironmentPort(private val property: Property) : Port {
  override fun invoke(default: Int): Int = try {
    parseInt(property())
  } catch (nfe: NumberFormatException) {
    default
  }
}

interface HttpPort : (Port) -> Unit

private val Vertx.router get() = router(this)

private fun Router.chain(vararg routes: HttpRoute) = this.apply {
  route().handler(BodyHandler.create())
  routes.forEach { it(this) }
}

class HttpServer(private val vertx: Vertx, private vararg val routes: HttpRoute) : HttpPort {
  private val log = getLogger(javaClass)

  override fun invoke(port: Port) {
    vertx
      .createHttpServer()
      .requestHandler(vertx.router.chain(*routes))
      .listen(port(8080)) {
        when {
          it.succeeded() -> log.info("Http Server started on port $port.")
          it.failed() -> log.error("Http Server failed to start on port $port.")
        }
      }
  }
}
