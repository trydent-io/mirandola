package io.trydent.olimpo.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger

interface HttpServer : (Int) -> Unit

private val Vertx.router get() = router(this)

private fun Router.chain(vararg resources: HttpResource): Router {
  resources.forEach { resource -> resource(this) }
  return this
}

class OlimpoHttpServer(private val vertx: Vertx, private vararg val resources: HttpResource) : HttpServer {
  private val log = getLogger(javaClass)

  override fun invoke(port: Int) {
    vertx.createHttpServer()
      .requestHandler(vertx.router.chain(*resources))
      .listen(port) {
        when {
          it.succeeded() -> log.info("Olimpo Http Server started on port $port.")
          it.failed() -> log.error("Olimpo Http Server failted to start on port $port.")
        }
      }
  }
}
