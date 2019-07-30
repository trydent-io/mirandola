package io.trydent.olimpo.apollo;

import io.trydent.olimpo.bus.Command;
import io.trydent.olimpo.bus.Process;
import io.trydent.olimpo.db.DbmsClient;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public interface ApolloCommand extends Command {
  static Command addReading(final DbmsClient client) {
    return new AddReading(client);
  }
}

final class AddReading implements Command {
  private static final Logger log = getLogger(AddReading.class);
  private static final String ADD_READING = "add-reading-command";
  private final DbmsClient dbmsClient;

  AddReading(final DbmsClient dbmsClient) {
    this.dbmsClient = dbmsClient;
  }

  @Override
  public final void executedBy(final Process process) {
    final var client = dbmsClient.get();
    process.consume(ADD_READING, json -> client.close());
  }
}
