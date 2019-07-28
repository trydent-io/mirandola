package io.trydent.olimpo.apollo;

import io.trydent.olimpo.bus.Command;
import io.trydent.olimpo.bus.Process;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

interface ApolloCommand extends Command {
  static Command addReading() {
    return new AddReading();
  }
}

final class AddReading implements Command {
  private static final Logger log = getLogger(AddReading.class);
  private static final String ADD_READING = "add-reading-command";

  @Override
  public final void accept(final Process process) {
    process.accept(ADD_READING, json -> {
      log.info("Add reading: $it");
    });
  }
}
