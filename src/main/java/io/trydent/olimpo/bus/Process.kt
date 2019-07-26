package io.trydent.olimpo.bus

import io.trydent.olimpo.sys.Id
import io.trydent.olimpo.vertx.Json
import io.trydent.olimpo.vertx.json
import io.vertx.core.eventbus.EventBus

interface Process : (String, Execution) -> Unit {
  companion object {
    fun commandProcess(id: Id, bus: EventBus): Process = CommandProcess(id, bus)
  }
}

internal class CommandProcess(private val id: Id, private val bus: EventBus) : Process {
  private val message by lazy {
    json(
      "id" to id()
    )
  }

  override fun invoke(name: String, execution: Execution) {
    bus.localConsumer<Json>(name) { message ->
      message.reply(this.message)
      execution(message.body())
    }
  }
}
