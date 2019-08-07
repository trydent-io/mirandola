package io.trydent.olimpo.apollo;

import io.trydent.olimpo.sink.Command;
import io.trydent.olimpo.db.SqlClient;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public interface ApolloCommand extends Command {
  static Command addReading(final SqlClient client) {
    return new AddReading(client);
  }
}

final class AddReading implements Command {
  private static final Logger log = getLogger(AddReading.class);
  private final SqlClient sqlClient;

  AddReading(final SqlClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  @Override
  public final void execute(JsonObject params) {
    final var sqlClient = this.sqlClient.get();
    sqlClient.query("select * from dual", async -> log.info("Done something"));
  }
}
