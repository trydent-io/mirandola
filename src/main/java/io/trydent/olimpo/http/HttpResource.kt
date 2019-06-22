package io.trydent.olimpo.http

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler

interface HttpResource : (Router) -> Router

class WebrootResource(private val path: String) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    route(path).handler(StaticHandler.create())
  }
}

class HelloworldResource(private val path: String) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    get(path)
      .produces("application/json")
      .handler {
        it.response()
          .putHeader("content-type", "application/json")
          .end(JsonObject().put("message", "Hello world.").toBuffer())
      }
  }
}
