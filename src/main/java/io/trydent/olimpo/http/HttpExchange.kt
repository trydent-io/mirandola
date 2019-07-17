package io.trydent.olimpo.http

import io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON
import io.trydent.olimpo.http.HttpHeader.ContentType
import io.trydent.olimpo.http.HttpValue.ApplicationJson
import io.trydent.olimpo.http.media.Json
import io.trydent.olimpo.http.media.json
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler

enum class HttpHeader(private val value: String) {
  ContentType("${HttpHeaders.CONTENT_TYPE}");

  override fun toString() = value
}

enum class HttpValue(private val value: String) {
  ApplicationJson("$APPLICATION_JSON");

  override fun toString() = value
}


interface HttpExchange : () -> Handler<RoutingContext> {
  companion object {
    fun staticContent(folder: String): HttpExchange = StaticContent(folder)
  }
}

fun String.asWebroot(): Handler<RoutingContext> = StaticHandler.create(this)

fun HttpServerResponse.end(json: Json) = this.end(json.toBuffer())
fun HttpServerResponse.end(json: Json, handler: Handler<AsyncResult<Void>>) = this.end(json.toBuffer(), handler)

fun HttpServerResponse.headers(vararg headers: Pair<HttpHeader, HttpValue>) = this.apply {
  headers.forEach { (header, value) -> this.putHeader("$header", "$value") }
}

class StaticContent(private val folder: String) : HttpExchange {
  override fun invoke() = folder.asWebroot()
}

class HelloExchange(private val dest: String) : HttpExchange {
  override fun invoke() = Handler<RoutingContext> {
    it.response()
      .headers(
        ContentType to ApplicationJson
      )
      .end(
        json(
          "message" to "Hello $dest!"
        )
      )
  }
}
