package io.trydent.olimpo.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger
import io.vertx.core.http.HttpServer as VxHttpServer

interface HttpServer : (Vertx, Int) -> VxHttpServer

private val Vertx.router get() = router(this)

private fun Router.chain(vararg routes: HttpRoute) = this.apply { routes.forEach { it(this) } }

class OlimpoHttpServer(private vararg val routes: HttpRoute) : HttpServer {
  private val log = getLogger(javaClass)

  override fun invoke(vertx: Vertx, port: Int): VxHttpServer = vertx
    .createHttpServer()
    .requestHandler(vertx.router.chain(*routes))
    .listen(port) {
      when {
        it.succeeded() -> log.info("Olimpo Http Server started on port $port.")
        it.failed() -> log.error("Olimpo Http Server failted to start on port $port.")
      }
    }
}
