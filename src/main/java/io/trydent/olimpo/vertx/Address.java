package io.trydent.olimpo.vertx;

import java.util.Formatter;
import java.util.function.Function;

public interface Address extends Function<String, String> {
  static Address templateAddress(final String template) {
    return new TemplateAddress(
      new Formatter(),
      template
    );
  }
}

final class TemplateAddress implements Address {
  private final Formatter formatter;
  private final String format;

  TemplateAddress(final Formatter formatter, final String format) {
    this.formatter = formatter;
    this.format = format;
  }

  @Override
  public final String apply(final String value) {
    return formatter.format(format, value).toString();
  }
}
