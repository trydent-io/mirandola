package io.trydent.mirandola

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class RootRequest : Handler<RoutingContext> {
  override fun handle(context: RoutingContext) = context
    .response()
    .putHeader("content-type", "application/json")
    .write(Message("Hello world.").asBuffer)
    .end()
}
