package io.trydent.olimpo.bus;

import java.util.function.Consumer;

public interface Command extends Consumer<Process> {
  @Override
  default void accept(Process process) {
    this.executedBy(process);
  }

  void executedBy(Process process);
}
