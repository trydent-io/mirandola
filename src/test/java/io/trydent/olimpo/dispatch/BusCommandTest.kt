package io.trydent.olimpo.dispatch

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.apollo.Id.Companion.id
import io.trydent.olimpo.apollo.Id.Companion.uuid
import io.trydent.olimpo.vertx.Json
import io.trydent.olimpo.vertx.json
import io.vertx.core.Future
import io.vertx.core.Future.*
import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*

internal class BusCommandTest {
  private val bus: EventBus = mockk {
    every { request<Json>(any(), any(), any()) } returns this
  }

  @Test
  internal fun `should execute any command`() {
    val command = BusCommand(bus = bus)

    assertThat(command("command-name", json()).future()).isNotNull

    verify { bus.request<Json>("command-name-command", json(), any()) }
  }
}

@ExtendWith(VertxExtension::class)
internal class BusCommandIT(vertx: Vertx) {
  val bus = vertx.eventBus()
  private val command = BusCommand(bus)

  @Test
  @Timeout(2, timeUnit = SECONDS)
  internal fun `should execute any command and have an id`(test: VertxTestContext) {
    bus.localConsumer<Json>("command-name-command") {
      it.reply(
        json(
          "id" to id("uuid")()
        )
      )
      test.completeNow()
    }

    val future = command("command-name", json()).future().compose { res ->
      assertThat(res()).isEqualTo("uuid")
      //test.awaitCompletion(6, SECONDS)
      succeededFuture<Json>()
    }

  }
}
