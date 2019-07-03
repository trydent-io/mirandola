package io.trydent.olimpo.http

import io.vertx.ext.web.Router

interface HttpResource : (Router) -> Router

class WebrootResource(private val path: String, private val request: WebrootRequest) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(request())
  }
}

class HelloResource(private val path: String, private val request: HelloRequest) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces("application/json")
      .handler(request())
  }
}
