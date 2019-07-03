package io.trydent.olimpo.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory.getLogger
import io.vertx.core.http.HttpServer as VxHttpServer

interface HttpServer : (Int) -> VxHttpServer

private val Vertx.router get() = router(this)

private fun Router.chain(vararg routes: HttpRoute) = this.apply {
  route().handler(BodyHandler.create())
  routes.forEach { it(this) }
}

class OlimpoHttpServer(private val vertx: Vertx, private vararg val routes: HttpRoute) : HttpServer {
  private val log = getLogger(javaClass)

  override fun invoke(port: Int): VxHttpServer = vertx
    .createHttpServer()
    .requestHandler(vertx.router.chain(*routes))
    .listen(port) {
      when {
        it.succeeded() -> log.info("Http Server started on port $port.")
        it.failed() -> log.error("Http Server failed to start on port $port.")
      }
    }
}
