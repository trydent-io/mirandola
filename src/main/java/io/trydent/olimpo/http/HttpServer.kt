package io.trydent.olimpo.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger
import io.vertx.core.http.HttpServer as VxHttpServer

interface HttpServer : (Int) -> VxHttpServer

private val Vertx.router get() = router(this)

private fun Router.chain(vararg resources: HttpResource) = this.apply { resources.forEach { it(this) } }

class OlimpoHttpServer(private val vertx: Vertx, private vararg val resources: HttpResource) : HttpServer {
  private val log = getLogger(javaClass)

  override fun invoke(port: Int): VxHttpServer = vertx
    .createHttpServer()
    .requestHandler(vertx.router.chain(*resources))
    .listen(port) {
      when {
        it.succeeded() -> log.info("Olimpo Http Server started on port $port.")
        it.failed() -> log.error("Olimpo Http Server failted to start on port $port.")
      }
    }
}
