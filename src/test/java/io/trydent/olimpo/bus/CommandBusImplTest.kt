package io.trydent.olimpo.bus

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.bus.DummyCommand.*
import io.trydent.olimpo.http.media.json
import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.junit5.VertxExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

enum class DummyCommand : Command {
  Send
}

@ExtendWith(VertxExtension::class)
internal class CommandBusImplTest {
  private val id: Id = mockk()
  private val eventBus: EventBus = mockk()
  private val commands = CommandBusImpl(id = id, eventBus = eventBus)

  @Test
  internal fun `should send a command`(vertx: Vertx) {
    every { id() } returns "commandId"
    every { eventBus.publish(any(), any()) } returns eventBus

    val params = json(
      "param" to "value"
    )

    @Suppress("UsePropertyAccessSyntax")
    assertThat(commands.send(Send, params)).isEqualTo("commandId")

    val expected = params + "id" to "commandId"

    verify { eventBus.publish("$Send", expected) }
  }
}
