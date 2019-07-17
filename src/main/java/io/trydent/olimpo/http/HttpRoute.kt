package io.trydent.olimpo.http

import io.trydent.olimpo.http.HttpValue.ApplicationJson
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

interface HttpRoute : (Router) -> Router {
  companion object {
    fun webroot(path: String, exchange: HttpExchange): HttpRoute = WebrootRoute(path, exchange)
  }
}

fun Route.produces(value: HttpValue): Route = this.produces("$value")

class WebrootRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(exchange())
  }
}

class HelloRoute(private val path: String, private val exchange: HelloExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces(ApplicationJson)
      .handler(exchange())
  }
}
