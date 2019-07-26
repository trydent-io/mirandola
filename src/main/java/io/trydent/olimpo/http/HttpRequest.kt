package io.trydent.olimpo.http

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router.router
import org.slf4j.LoggerFactory.getLogger

interface HttpRequest : () -> Handler<HttpServerRequest> {
  companion object {
    fun routeSwitch(vertx: Vertx, vararg routes: HttpRoute): HttpRequest = HttpRouteSwitch(vertx, *routes)

    fun single(vertx: Vertx, route: HttpRoute): HttpRequest = HttpSingle(vertx, route)
  }
}

private fun Vertx.createRouter() = router(this)

internal class HttpRouteSwitch(private val vertx: Vertx, private vararg val switchRoutes: HttpRoute) : HttpRequest {
  private val log = getLogger(javaClass)

  override fun invoke(): Handler<HttpServerRequest> = vertx.createRouter().apply {
    switchRoutes.forEach { route -> route(this) }
  }
}

internal class HttpSingle(private val vertx: Vertx, private val route: HttpRoute) : HttpRequest {
  private val log = getLogger(javaClass)

  override fun invoke(): Handler<HttpServerRequest> = vertx.createRouter().apply {
    route(this).errorHandler(500) { log.error(it.failure().message) }
  }
}
