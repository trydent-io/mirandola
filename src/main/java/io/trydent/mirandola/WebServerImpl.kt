package io.trydent.mirandola

import io.vertx.core.http.HttpServer

interface WebServer : () -> Unit

internal class WebServerImpl(
  private val httpServer: HttpServer,
  val host: String,
  val port: Int
) : WebServer {
  override fun invoke() {
    httpServer
      .requestHandler { it.response().end() }
      .listen(port, host)
  }
}
