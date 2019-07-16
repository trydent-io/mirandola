package io.trydent.olimpo.io

import io.mockk.every
import io.mockk.mockk
import io.trydent.olimpo.sys.Property
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class EnvPortTest {
  private val environmentVariable: Property = mockk()

  @Test
  internal fun `should get environment variable`() {
    every { environmentVariable() } returns "8080"

    val port = EnvPort(environmentVariable)

    assertThat(port(8090)).isNotEqualTo(8090)
  }

  @Test
  internal fun `should get default when environment variable is null`() {
    every { environmentVariable() } returns null

    val port = EnvPort(environmentVariable)

    assertThat(port(8090)).isEqualTo(8090)
  }
}
