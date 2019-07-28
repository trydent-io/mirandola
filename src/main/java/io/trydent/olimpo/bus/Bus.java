package io.trydent.olimpo.bus;

import java.util.Arrays;

public interface Bus extends Runnable {
  static Bus commandBus(final Process process, final Command... commands) {
    return new CommandBus(process, Arrays.copyOf(commands, commands.length));
  }
}

final class CommandBus implements Bus {
  private final Process process;
  private final Command[] commands;

  CommandBus(final Process process, final Command[] commands) {
    this.process = process;
    this.commands = commands;
  }

  @Override
  public final void run() {
    for (var command : commands) {
      command.accept(process);
    }

  }
}
