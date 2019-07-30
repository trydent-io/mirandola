package io.trydent.olimpo.bus;

import io.trydent.olimpo.sys.Id;
import io.vertx.core.eventbus.EventBus;

import java.util.Arrays;

public interface Bus extends Runnable {
  static Bus commandBus(final EventBus bus, final Command... commands) {
    return new CommandBus(
      new CommandProcess(
        Id.uuid(),
        bus
      ),
      Arrays.copyOf(commands, commands.length)
    );
  }

  @Override
  default void run() {
    this.listen();
  }

  void listen();
}

final class CommandBus implements Bus {
  private final Process process;
  private final Command[] commands;

  CommandBus(final Process process, final Command[] commands) {
    this.process = process;
    this.commands = commands;
  }

  @Override
  public final void listen() {
    for (var command : commands) {
      command.executedBy(process);
    }
  }
}
