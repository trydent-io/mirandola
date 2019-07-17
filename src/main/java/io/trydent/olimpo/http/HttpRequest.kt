package io.trydent.olimpo.http

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router.router

interface HttpRequest : () -> Handler<HttpServerRequest> {
  companion object {
    fun switch(vararg routes: HttpRoute): HttpRequest = HttpSwitch(
      vertx = vertx(),
      switchRoutes = *routes
    )
  }
}

private fun Vertx.createRouter() = router(this)

class HttpSwitch(private val vertx: Vertx, private vararg val switchRoutes: HttpRoute) : HttpRequest {
  override fun invoke(): Handler<HttpServerRequest> = vertx.createRouter().apply {
    switchRoutes.forEach { route -> route(this) }
  }
}
