package io.trydent.mirandola

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class RootRequestTest {
  private val context: RoutingContext = mockk()
  private val response: HttpServerResponse = mockk()

  @Test
  internal fun `should do something`() {
    every { context.response() } returns response
    every { response.putHeader(any(), any<String>()) } returns response
    every { response.write(any<Buffer>()) } returns response
    every { response.end() } returns Unit

    val request = RootRequest()

    request.handle(context)
    verify { context.response() }
    verify { response.putHeader("content-type", "application/json") }
    verify { response.write(Message("Hello world.").asBuffer) }
  }
}
