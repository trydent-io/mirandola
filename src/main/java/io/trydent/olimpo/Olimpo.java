package io.trydent.olimpo;

import static io.trydent.olimpo.action.Action.commandAction;
import static io.trydent.olimpo.apollo.ApolloCommand.addReading;
import static io.trydent.olimpo.db.DbmsParams.envPostgresql;
import static io.trydent.olimpo.db.SqlClient.dbmsClient;
import static io.trydent.olimpo.http.HttpExchange.actionSwitch;
import static io.trydent.olimpo.http.HttpExchange.staticContent;
import static io.trydent.olimpo.http.HttpRequest.routeSwitch;
import static io.trydent.olimpo.http.HttpRoute.action;
import static io.trydent.olimpo.http.HttpRoute.webroot;
import static io.trydent.olimpo.http.HttpServer.httpServer;
import static io.trydent.olimpo.io.Port.envPort;
import static io.trydent.olimpo.sink.CommandSink.commandBus;
import static io.trydent.olimpo.sys.Id.uuid;
import static io.trydent.olimpo.vertx.Deployment.verticles;

public final class Olimpo {
  private Olimpo() {
  }

  public static void main(String... args) {
    verticles(
      vertx -> httpServer(
        vertx,
        routeSwitch(
          vertx,
          webroot("/*",
            staticContent(
              "webroot"
            )
          ),
          action("/apollo/:action",
            actionSwitch(
              commandAction(vertx.eventBus())
            )
          )
        )
      ).listen(envPort("PORT", 8095)),
      vertx -> commandBus(vertx.eventBus(), uuid())
        .let("add-reading",
          addReading(
            dbmsClient(
              vertx,
              envPostgresql("DATABASE_URL")
            )
          )
        )
    ).deploy();
  }
}
