package io.trydent.olimpo.http

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler

interface HttpResource : () -> Router

class WebrootResource(private val path: String, private val router: Router) : HttpResource {
  override fun invoke(): Router {
    router.route(path).handler(StaticHandler.create())
    return router
  }
}
