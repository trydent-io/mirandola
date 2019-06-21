package io.trydent.olimpo.http

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface HttpServer : (Int) -> Unit

class OlimpoHttpServer(private val vertx: Vertx, private val router: Router, vararg resources: HttpResource) : HttpServer {
  private val log: Logger by lazy { getLogger(javaClass) }

  private val resources = resources

  override fun invoke(port: Int) {
    vertx.createHttpServer()
      .requestHandler(router.let { resources.forEach { resource -> resource(it) }; it })
      .listen(port) {
        when {
          it.succeeded() -> log.info("Olimpo Http Server started on port $port.")
          it.failed() -> log.error("Olimpo Http Server failted to start on port $port.")
        }
      }
  }
}
