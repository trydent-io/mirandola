package io.trydent.olimpo.http

import io.vertx.ext.web.Router

interface HttpResource : (Router) -> Router

class WebrootResource(private val path: String, private val request: WebrootExchange) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(request())
  }
}

class HelloResource(private val path: String, private val request: HelloExchange) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces("application/json")
      .handler(request())
  }
}
