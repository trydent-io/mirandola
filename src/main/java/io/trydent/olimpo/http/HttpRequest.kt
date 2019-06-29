package io.trydent.olimpo.http

import io.trydent.olimpo.http.media.Json.json
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler

interface HttpRequest : () -> Handler<RoutingContext>

fun String.asWebroot(): Handler<RoutingContext> = StaticHandler.create(this)

fun HttpServerResponse.headers(vararg headers: Pair<String, String>) = this.apply {
  headers.forEach { (header, value) -> this.putHeader(header, value) }
}

class WebrootRequest(private val resources: String) : HttpRequest {
  override fun invoke() = resources.asWebroot()
}

class HelloRequest(private val dest: String) : HttpRequest {
  override fun invoke() = Handler<RoutingContext> {
    it.response()
      .headers(
        "content-type" to "application/json"
      )
      .end(
        json(
          "message" to "Hello $dest!"
        )
      )
  }
}
