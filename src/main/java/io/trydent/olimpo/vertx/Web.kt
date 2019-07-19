package io.trydent.olimpo.vertx

import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

enum class HttpHeader(private val value: String) {
  ContentType("${HttpHeaders.CONTENT_TYPE}");

  override fun toString() = value
}

enum class HttpValue(private val value: String) {
  ApplicationJson("${HttpHeaderValues.APPLICATION_JSON}");

  override fun toString() = value
}

fun Route.consumes(value: HttpValue) = this.consumes("$value")
fun Route.produces(value: HttpValue) = this.produces("$value")

fun HttpServerResponse.end(json: Json) = this.end(json.toBuffer())
fun HttpServerResponse.end(json: Json, handler: Handler<AsyncResult<Void>>) = this.end(json.toBuffer(), handler)

fun HttpServerResponse.headers(vararg headers: Pair<HttpHeader, HttpValue>) = this.apply {
  headers.forEach { (header, value) -> this.putHeader("$header", "$value") }
}

