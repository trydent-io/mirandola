package io.trydent.olimpo.bus

import io.trydent.olimpo.http.media.Json
import io.vertx.core.eventbus.EventBus

interface Bus<T> {
  fun dispatch(entity: T, params: Json): String
}

interface CommandBus : Bus<Command> {
  override fun dispatch(entity: Command, params: Json): String = send(entity, params)

  fun send(command: Command, params: Json): String
}

class CommandBusImpl(private val id: Id, private val eventBus: EventBus) : CommandBus {
  override fun send(command: Command, params: Json) = id().apply {
    eventBus.publish("$command", params + "id" to this)
  }
}
