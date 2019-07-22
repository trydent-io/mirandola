package io.trydent.olimpo.dispatch

import io.trydent.olimpo.apollo.Id
import io.trydent.olimpo.apollo.Id.Companion.id
import io.trydent.olimpo.vertx.Json
import io.vertx.core.AsyncResult
import io.vertx.core.Promise
import io.vertx.core.Promise.promise
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory.getLogger
import java.lang.RuntimeException

interface AsyncCommand : (String, Json) -> Promise<Id> {
  companion object {
    fun busCommand(bus: EventBus): AsyncCommand = BusCommand(bus)
  }
}

internal class BusCommand(private val bus: EventBus) : AsyncCommand {
  private val log = getLogger(javaClass)

  override fun invoke(name: String, params: Json): Promise<Id> {
    val promise = promise<Id>()
    val commandName = "$name-command"
    bus.request<Json>(commandName, params) {
      when {
        it.succeeded() -> it.id.apply {
          log.info("Command `$commandName` has started to process ${this}.")
          promise.complete(id(this))
        }
        it.failed() -> "Command `$commandName` has failed: ${it.cause().message}.".apply {
          log.info(this)
          promise.fail(RuntimeException(this))
        }
      }
    }
    return promise
  }
}

private val AsyncResult<Message<JsonObject>>.id get() = this.result().body().getString("id")
