package io.trydent.olimpo.io;

import io.trydent.olimpo.sys.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.trydent.olimpo.io.Port.portOrDie;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class EnvPortTest {
  private final Property envVar = mock(Property.class);

  @Test
  @DisplayName("should get environment variable")
  void shouldGetEnvironmentVariable() {
    when(envVar.get()).thenReturn("8080");

    final var port = new EnvPort(envVar, portOrDie(8090));

    assertThat(port.get()).isNotEqualTo(8090);
    assertThat(port.toString()).isEqualTo("8080");
  }

  @Test
  @DisplayName("should get default when environment variable is null")
  void shouldGetDefault() {
    when(envVar.get()).thenReturn(null);

    final var port = new EnvPort(envVar, portOrDie(8090));

    assertThat(port.get()).isEqualTo(8090);
    assertThat(port.toString()).isEqualTo("8090");
  }
}
