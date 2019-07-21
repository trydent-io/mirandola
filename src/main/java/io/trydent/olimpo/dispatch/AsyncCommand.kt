package io.trydent.olimpo.dispatch

import io.trydent.olimpo.apollo.Id
import io.trydent.olimpo.apollo.Id.Companion.id
import io.trydent.olimpo.vertx.Json
import io.vertx.core.AsyncResult
import io.vertx.core.Future.failedFuture
import io.vertx.core.Promise
import io.vertx.core.Promise.promise
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory.getLogger

interface AsyncCommand : (String, Json) -> Promise<Id>
interface Event : (String, Json) -> Unit

class BusCommand(private val bus: EventBus) : AsyncCommand {
  private val log = getLogger(javaClass)

  override fun invoke(name: String, params: Json): Promise<Id> {
    val promise = promise<Id>()
    bus.request<Json>("$name-command", params) {
      when {
        it.succeeded() -> {
          log.info("Command `$name` has started to process ${it.id}.")
          promise.complete(id(it.id))
        }
        it.failed() -> {
          val message = "Command `$name` has failed: ${it.cause().message}."
          log.info(message)
          promise.fail(message)
        }
      }
    }
    return promise
  }
}

private val AsyncResult<Message<JsonObject>>.id get() = this.result().body().getString("id")
