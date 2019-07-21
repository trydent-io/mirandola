package io.trydent.olimpo.http

import io.trydent.olimpo.vertx.HttpValue
import io.trydent.olimpo.vertx.HttpValue.ApplicationJson
import io.trydent.olimpo.vertx.consumes
import io.trydent.olimpo.vertx.produces
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

interface HttpRoute : (Router) -> Router {
  companion object {
    fun webroot(path: String, exchange: HttpExchange): HttpRoute = WebrootRoute(path, exchange)
    fun action(path: String, exchange: HttpExchange): HttpRoute = ActionRoute(path, exchange)
  }
}

internal class WebrootRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  override fun invoke(router: Router) = router.apply {
    get(path).handler(exchange())
  }
}

internal class ActionRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  override fun invoke(router: Router): Router = router.apply {
    post(path)
      .consumes(ApplicationJson)
      .produces(ApplicationJson)
      .handler(exchange())
  }
}
