package io.trydent.olimpo.http

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.dispatch.CommandId
import io.trydent.olimpo.vertx.json
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import org.junit.jupiter.api.Test

internal class ActionExecutionTest {
  private val command: Command = mockk()
  private val context: RoutingContext = mockk()
  private val response: HttpServerResponse = mockk()

  @Test
  internal fun `should handle an action and translate it to command`() {
    every { command(any(), any()) } returns CommandId()
    every { context.bodyAsJson } returns json()
    every { context.response() } returns response
    every { response.putHeader(any(), any<String>()) } returns response
    every { response.end(any<Buffer>()) } returns Unit

    val action = ActionExecution(command = command)

    action().handle(context)

    verify { command(any(), any()) }
  }
}
