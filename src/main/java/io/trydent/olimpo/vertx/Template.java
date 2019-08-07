package io.trydent.olimpo.vertx;

import java.util.Formatter;
import java.util.function.Function;

public interface Template extends Function<String, String> {
  static Template template(final String template) {
    return new FormattedTemplate(
      new Formatter(),
      template
    );
  }

  static Template commandName() {
    return template("%s-command");
  }
}

final class FormattedTemplate implements Template {
  private final Formatter formatter;
  private final String format;

  FormattedTemplate(final Formatter formatter, final String format) {
    this.formatter = formatter;
    this.format = format;
  }

  @Override
  public final String apply(final String value) {
    return formatter.format(format, value).toString();
  }
}
