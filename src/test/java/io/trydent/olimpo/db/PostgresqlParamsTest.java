package io.trydent.olimpo.db;

import io.trydent.olimpo.sys.Property;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostgresqlParamsTest {
  private final Property property = mock(Property.class);
  private final DbParams params = new PostgresqlParams(property, Driver.class, "jdbc:postgresql://%s:%d%s");

  @Test
  @DisplayName("should get dbms params for PostgreSQL")
  void shouldGetParams() {
    when(property.get()).thenReturn("postgres://username:password@anyhost:1234/database");

    final var json = params.get();
    assertThat(json.getString("url")).isEqualTo("jdbc:postgresql://anyhost:1234/database");
    assertThat(json.getString("user")).isEqualTo("username");
    assertThat(json.getString("password")).isEqualTo("password");
    assertThat(json.getString("driver_class")).isEqualTo(Driver.class.getName());
  }
}
