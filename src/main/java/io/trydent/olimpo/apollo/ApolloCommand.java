package io.trydent.olimpo.apollo;

import io.trydent.olimpo.sink.Command;
import io.trydent.olimpo.db.DbClient;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public interface ApolloCommand extends Command {
  static Command addReading(final DbClient client) {
    return new AddReading(client);
  }
}

final class AddReading implements Command {
  private static final Logger log = getLogger(AddReading.class);
  private final DbClient dbClient;

  AddReading(final DbClient dbClient) {
    this.dbClient = dbClient;
  }

  @Override
  public final void execute(JsonObject params) {
    final var sqlClient = this.dbClient.get();
    sqlClient.query("select * from dual", async -> log.info("Done something"));
  }
}
