package io.trydent.olimpo.bus

import io.trydent.olimpo.sys.Id
import io.trydent.olimpo.sys.Id.Companion.id
import io.trydent.olimpo.vertx.Json
import io.vertx.core.AsyncResult
import io.vertx.core.Promise
import io.vertx.core.Promise.promise
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory.getLogger

interface Task : (String, Json) -> Promise<Id> {
  companion object {
    fun command(bus: EventBus): Task = CommandTask(bus)
  }
}

internal class CommandTask(private val bus: EventBus) : Task {
  private val log = getLogger(javaClass)

  override fun invoke(name: String, params: Json): Promise<Id> = promise<Id>().apply {
    val commandName = "$name-command"

    bus.request<Json>(commandName, params) { async ->
      when {
        async.succeeded() -> {
          log.info("Command `$commandName` has started to process ${async.message["id"]}.")
          complete(id(async.message["id"]))
        }
        async.failed() -> "Command `$commandName` has failed: ${async.cause().message}.".apply {
          fail(this)
          async.cause().printStackTrace()
        }
      }
    }
  }
}

private val AsyncResult<Message<JsonObject>>.message get() = this.result()
private val Message<JsonObject>.json get() = this.body()
private operator fun Message<JsonObject>.get(field: String): String = this.json.getString(field)
