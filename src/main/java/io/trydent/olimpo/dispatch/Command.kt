package io.trydent.olimpo.dispatch

import io.trydent.olimpo.apollo.Id
import io.trydent.olimpo.http.media.Json
import io.vertx.core.eventbus.EventBus
import java.util.UUID.randomUUID

class CommandId : Id {
  override fun invoke() = "${randomUUID()}"
}

interface Command : (String, Json) -> Id
interface Event : (String, Json) -> Unit

class BusCommand(private val bus: EventBus) : Command {
  override fun invoke(name: String, params: Json): Id {
    bus.publish("$name-command", params)
    return CommandId()
  }
}
