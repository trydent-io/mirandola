package io.trydent.olimpo.apollo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.dispatch.CommandId
import io.trydent.olimpo.http.media.json
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import org.junit.jupiter.api.Test

internal class AddReadingExchangeTest {
  private val command: Command = mockk()
  private val context: RoutingContext = mockk()
  private val response: HttpServerResponse = mockk()

  @Test
  internal fun `should send add-reading command`() {
    every { command(any(), any()) } returns CommandId()
    every { context.bodyAsJson } returns json()
    every { context.response() } returns response
    every { response.putHeader(any(), any<String>()) } returns response
    every { response.end(any<Buffer>()) } returns Unit

    val addReading = AddReadingExchange(command = command)

    addReading().handle(context)

    verify { command(any(), any()) }
  }
}
