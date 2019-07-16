package io.trydent.olimpo.dispatch

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.http.media.json
import io.vertx.core.eventbus.EventBus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BusCommandTest {
  private val bus: EventBus = mockk {
    every { publish(any(), any()) } returns this
  }

  @Test
  internal fun `should execute any command`() {
    val command = BusCommand(bus = bus)

    assertThat(command("command-name", json())).isNotEmpty()

    verify { bus.publish("command-name-command", json()) }
  }
}
