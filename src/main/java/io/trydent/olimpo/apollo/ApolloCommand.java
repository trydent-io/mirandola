package io.trydent.olimpo.apollo;

import io.trydent.olimpo.db.DbClient;
import io.trydent.olimpo.sink.Command;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;

import static io.trydent.olimpo.sys.Id.uuid;
import static io.trydent.olimpo.type.When.when;
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
    final var id = uuid();
    dbClient.get().updateWithParams("insert into readings(id, reading) values (?, ?)", new JsonArray().add(id.get()).add(params.toString()), async ->
      when(async.succeeded(),
        () -> log.info("Reading added: {}.", params.toString()),
        () -> log.error("Reading couldn't be added: {}.", async.cause().getMessage())
      )
    );
  }
}
