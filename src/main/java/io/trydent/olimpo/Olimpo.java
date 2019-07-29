package io.trydent.olimpo;

import static io.trydent.olimpo.action.Action.busCommand;
import static io.trydent.olimpo.apollo.ApolloCommand.addReading;
import static io.trydent.olimpo.bus.Bus.commandBus;
import static io.trydent.olimpo.bus.Process.commandProcess;
import static io.trydent.olimpo.http.HttpExchange.actionSwitch;
import static io.trydent.olimpo.http.HttpExchange.staticContent;
import static io.trydent.olimpo.http.HttpRequest.routeSwitch;
import static io.trydent.olimpo.http.HttpRoute.action;
import static io.trydent.olimpo.http.HttpRoute.webroot;
import static io.trydent.olimpo.http.HttpServer.httpServer;
import static io.trydent.olimpo.io.Port.envPort;
import static io.trydent.olimpo.sys.Id.uuid;
import static io.trydent.olimpo.vertx.Deployment.deploy;

public final class Olimpo {
  private Olimpo() {
  }

  public static void main(String... args) {
    deploy(
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
              busCommand(vertx.eventBus())
            )
          )
        )
      ).accept(envPort("PORT", 8095)),
      vertx -> commandBus(
        commandProcess(uuid(), vertx.eventBus()),
        addReading()
      ).run()
    ).run();
  }
}
