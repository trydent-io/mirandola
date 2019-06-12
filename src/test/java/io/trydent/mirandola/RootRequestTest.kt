package io.trydent.mirandola

import io.mockk.every
import io.mockk.mockk
import io.vertx.core.buffer.Buffer
import io.vertx.core.buffer.Buffer.buffer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import io.vertx.junit5.VertxExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class RootRequestTest {
  private val context: RoutingContext = mockk()
  private val response: FakeRootResponse = FakeRootResponse()
  private val message = Message("Hello world.").asBuffer

  @Test
  internal fun `should do something`() {
    every { context.response() } returns response

    RootRequest().handle(context)

    assertThat(response.headers["content-type"]).isEqualTo("application/json")
    assertThat(response.buffer).isEqualTo(message)
  }
}

private class FakeRootResponse : FakeResponse() {
  var headers = mutableMapOf<String, String>()
  var buffer = buffer()

  override fun putHeader(name: String?, value: String?) = this.also { headers[name!!] = value!! }

  override fun write(data: Buffer?): HttpServerResponse = this.also { buffer = data!! }

  override fun end() = Unit
}
