package io.trydent.olimpo.io;

import io.trydent.olimpo.sys.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.trydent.olimpo.io.Port.portOrDie;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class EnvPortTest {
  private final Property environmentVariable = mock(Property.class);

  @Test
  @DisplayName("should get environment variable")
  void shouldGetEnvironmentVariable() {
    when(environmentVariable.get()).thenReturn("8080");

    final var port = new EnvPort(environmentVariable, portOrDie(8090));

    assertThat(port.get()).isNotEqualTo(8090);
  }

  @Test
  @DisplayName("should get default when environment variable is null")
  void shouldGetDefault() {
    when(environmentVariable.get()).thenReturn(null);

    final var port = new EnvPort(environmentVariable, portOrDie(8090));

    assertThat(port.get()).isEqualTo(8090);
  }
}
