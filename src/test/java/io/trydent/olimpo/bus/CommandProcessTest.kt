package io.trydent.olimpo.bus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.sys.Id.Companion.id
import io.trydent.olimpo.vertx.json.Json
import io.vertx.core.eventbus.EventBus
import io.vertx.core.eventbus.MessageConsumer
import org.junit.jupiter.api.Test

internal class CommandProcessTest {
  private val eventBus: EventBus = mockk()
  private val consumer: MessageConsumer<Json> = mockk()

  @Test
  internal fun `should execute a command`() {
    every { eventBus.localConsumer<Json>("any-command", any()) } returns consumer

    val id = id("process-id")
    val process = CommandProcess(
      id,
      eventBus
    )

    process("any-command", execution = object : Execution {
      override fun invoke(json: Json) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    })

    verify {
      eventBus.localConsumer<Json>("any-command") {
        it.apply {
          reply(
            json(
              "processId" to id()
            )
          )
        }
      }
    }
  }
}
