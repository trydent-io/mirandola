package io.trydent.olimpo.http

import io.vertx.ext.web.handler.StaticHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HttpRequestTest {
  @Test
  internal fun `should retrieve webroot`() {
    assertThat("webroot".asWebroot()).isInstanceOf(StaticHandler::class.java)
  }

  @Test
  internal fun `should retrive json`() {
    val request = HelloRequest(dest = "world")

    assertThat(request()).isNotNull
  }
}

