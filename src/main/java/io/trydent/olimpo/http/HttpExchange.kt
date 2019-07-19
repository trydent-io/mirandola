package io.trydent.olimpo.http

import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.vertx.HttpHeader.*
import io.trydent.olimpo.vertx.HttpValue.*
import io.trydent.olimpo.vertx.end
import io.trydent.olimpo.vertx.headers
import io.trydent.olimpo.vertx.json
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler

interface HttpExchange : () -> Handler<RoutingContext> {
  companion object {
    fun staticContent(folder: String): HttpExchange = StaticContent(folder)

    fun actionExecution(command: Command): HttpExchange = ActionExecution(command)
  }
}

internal class StaticContent(private val folder: String) : HttpExchange {
  override fun invoke(): StaticHandler = StaticHandler.create(folder)
}

internal class ActionExecution(private val command: Command) : HttpExchange {
  override fun invoke() = Handler<RoutingContext> {
    it.request().bodyHandler { buffer ->
      it.response()
        .headers(
          ContentType to ApplicationJson
        )
        .end(
          json(
            "actionId" to command(it.params["action"], buffer.asJson)
          )
        )
    }
  }
}

private val Buffer.asJson get() = this.toJsonObject()

private val RoutingContext.params get() = this.request().params()
