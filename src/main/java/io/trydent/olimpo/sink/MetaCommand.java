package io.trydent.olimpo.sink;

import io.trydent.olimpo.vertx.Template;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.trydent.olimpo.vertx.Template.commandName;

public interface MetaCommand extends Consumer<BiConsumer<String, Command>> {
  static MetaCommand metaCommand(final String name, final Command command) {
    return new CommandProcess(name, command, commandName());
  }

  @Override
  default void accept(BiConsumer<String, Command> consumer) {
    this.process(consumer);
  }

  void process(BiConsumer<String, Command> consumer);
}

final class CommandProcess implements MetaCommand {
  private final String name;
  private final Command command;
  private final Template template;

  CommandProcess(final String name, final Command command, final Template template) {
    this.name = name;
    this.command = command;
    this.template = template;
  }

  @Override
  public final void process(final BiConsumer<String, Command> consumer) {
    consumer.accept(template.apply(name.toLowerCase()), command);
  }
}

