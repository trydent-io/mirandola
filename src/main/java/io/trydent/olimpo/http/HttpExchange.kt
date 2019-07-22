package io.trydent.olimpo.http

import io.trydent.olimpo.apollo.Id
import io.trydent.olimpo.apollo.Id.Companion.id
import io.trydent.olimpo.dispatch.AsyncCommand
import io.trydent.olimpo.vertx.HttpHeader.ContentType
import io.trydent.olimpo.vertx.HttpValue.ApplicationJson
import io.trydent.olimpo.vertx.end
import io.trydent.olimpo.vertx.headers
import io.trydent.olimpo.vertx.json
import io.vertx.core.CompositeFuture
import io.vertx.core.CompositeFuture.*
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import org.slf4j.LoggerFactory.getLogger

interface HttpExchange : () -> Handler<RoutingContext> {
  companion object {
    fun staticContent(folder: String): HttpExchange = StaticContent(folder)

    fun actionExecution(command: AsyncCommand): HttpExchange = ActionExecution(command)
  }
}

internal class StaticContent(private val folder: String) : HttpExchange {
  override fun invoke(): StaticHandler = StaticHandler.create(folder)
}

internal class ActionExecution(private val command: AsyncCommand) : HttpExchange {
  private val log = getLogger(javaClass)

  override fun invoke() = Handler<RoutingContext> { routing ->
    routing.request().bodyHandler { buffer ->
      command(routing.params["action"], buffer.asJson).future().setHandler { async ->
        when {
          async.succeeded() -> routing.response()
            .headers(
              ContentType to ApplicationJson
            )
            .end(
              json(
                "actionId" to id(async.result().invoke()).invoke()
              ).apply { log.info("${this}") }
            )
          async.failed() -> routing.fail(500)
        }
      }
    }
  }
}

private val Buffer.asJson get() = this.toJsonObject()

private val RoutingContext.params get() = this.request().params()
