package io.trydent.olimpo.http

import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

interface HttpRoute : (Router) -> Router {
  companion object {
    fun webroot(path: String, exchange: HttpExchange): HttpRoute = WebrootRoute(path, exchange)
  }
}

fun Route.produces(value: HttpValue): Route = this.produces("$value")

internal class WebrootRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(exchange())
  }
}
