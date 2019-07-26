package io.trydent.olimpo.action

import io.trydent.olimpo.bus.Task
import io.trydent.olimpo.sys.Id
import io.trydent.olimpo.sys.Id.Companion.id
import io.trydent.olimpo.vertx.Json
import io.vertx.core.Promise
import io.vertx.core.Promise.promise
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.EventBus
import java.time.LocalDateTime

interface Action : Task {
  companion object {
    fun busCommand(bus: EventBus): Action = BusCommand(bus)
  }
}

internal class BusCommand(private val bus: EventBus) : Action {
  override fun invoke(name: String, params: Json): Promise<Id> {
    val promise = promise<Id>()
    bus.request<Json>("$name-command", params, DeliveryOptions().apply {
      addHeader("submitted", "${LocalDateTime.now()}")
      isLocalOnly = true
    }) {
      when {
        it.succeeded() -> promise.complete(id(it.result().body().getString("id")))
      }
    }
    return promise
  }
}
