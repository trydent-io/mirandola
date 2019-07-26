package io.trydent.olimpo.http

import io.trydent.olimpo.action.Action
import io.trydent.olimpo.sys.Id
import io.trydent.olimpo.sys.Id.Companion.id
import io.trydent.olimpo.vertx.HttpHeader.ContentType
import io.trydent.olimpo.vertx.HttpValue.ApplicationJson
import io.trydent.olimpo.vertx.Json
import io.trydent.olimpo.vertx.end
import io.trydent.olimpo.vertx.headers
import io.trydent.olimpo.vertx.json
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import org.slf4j.LoggerFactory.getLogger

interface HttpExchange : () -> Handler<RoutingContext> {
  companion object {
    fun staticContent(folder: String): HttpExchange = StaticContent(folder)

    fun actionSwitch(vararg actions: Action): HttpExchange = ActionSwitch(arrayOf(*actions))
  }
}

internal class StaticContent(private val folder: String) : HttpExchange {
  override fun invoke(): StaticHandler = StaticHandler.create(folder)
}

internal class ActionSwitch(private val actions: Array<Action>) : HttpExchange {
  private val log = getLogger(javaClass)

  override fun invoke() = Handler<RoutingContext> { routing ->
    routing.request().bodyHandler { buffer ->
      actions
        .map { action -> action(routing.params["action"], buffer.asJson) }
        .map { promise -> promise.future() }
        .firstOrNull { future -> future.failed().not() }
        ?.also { future -> asyncResponse(future, routing) }
        ?: routing.fail(500)
    }
  }

  private fun asyncResponse(future: Future<Id>, routing: RoutingContext) {
    future.setHandler { response(routing, it) }
  }

  private fun response(routing: RoutingContext, async: AsyncResult<Id>) = routing
    .response()
    .headers(
      ContentType to ApplicationJson
    )
    .end(
      json(
        "actionId" to id(async.result().invoke()).invoke()
      ).apply { log.info("${this}") }
    )
}

private val Buffer.asJson get() = this.toJsonObject()

private val RoutingContext.params get() = this.request().params()
