package io.trydent.olimpo.http

import io.vertx.ext.web.Router

interface HttpRoute : (Router) -> Router

class WebrootRoute(private val path: String, private val request: WebrootExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(request())
  }
}

class HelloRoute(private val path: String, private val request: HelloExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces("application/json")
      .handler(request())
  }
}
