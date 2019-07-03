package io.trydent.olimpo.http

import io.trydent.olimpo.http.HttpValue.ApplicationJson
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

interface HttpRoute : (Router) -> Router

fun Route.produces(value: HttpValue): Route = this.produces("$value")

class WebrootRoute(private val path: String, private val request: WebrootExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(request())
  }
}

class HelloRoute(private val path: String, private val request: HelloExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces(ApplicationJson)
      .handler(request())
  }
}
