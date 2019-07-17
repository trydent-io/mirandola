package io.trydent.olimpo.http

import io.vertx.ext.web.handler.StaticHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HttpExchangeTest {
  @Test
  internal fun `should retrieve webroot`() {
    assertThat("webroot".asWebroot()).isInstanceOf(StaticHandler::class.java)
  }
}

