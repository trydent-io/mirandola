package io.trydent.olimpo.http

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpMethod.*
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.impl.RoutingContextImpl
import org.slf4j.LoggerFactory.getLogger

interface HttpRequest : () -> Handler<HttpServerRequest> {
  companion object {
    fun switch(vertx: Vertx, vararg routes: HttpRoute): HttpRequest = HttpSwitch(vertx, *routes)

    fun single(vertx: Vertx, route: HttpRoute): HttpRequest = HttpSingle(vertx, route)
  }
}

private fun Vertx.createRouter() = router(this)

internal class HttpSwitch(private val vertx: Vertx, private vararg val switchRoutes: HttpRoute) : HttpRequest {
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
