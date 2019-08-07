package io.trydent.olimpo.http;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.trydent.olimpo.action.Action.commandAction;
import static io.trydent.olimpo.http.HttpExchange.actionSwitch;
import static io.trydent.olimpo.http.HttpRequest.single;
import static io.trydent.olimpo.http.HttpRoute.action;
import static io.trydent.olimpo.http.HttpServer.httpServer;
import static io.trydent.olimpo.io.Port.portOrDie;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;
import static io.trydent.olimpo.vertx.json.JsonString.jsonString;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;

@ExtendWith(VertxExtension.class)
class HttpActionIT {
  private final EventBus bus;
  private final HttpServer httpServer;

  HttpActionIT(Vertx vertx) {
    this.bus = vertx.eventBus();
    this.httpServer = httpServer(
      vertx,
      single(
        vertx,
        action(
          "/api/apollo/:action",
          actionSwitch(
            commandAction(this.bus)
          )
        )
      )
    );
  }

  @Test
  @DisplayName("should execute any-action")
  @Timeout(value = 4, timeUnit = SECONDS)
  void shouldExecuteAnyAction(VertxTestContext test) {
    httpServer.accept(portOrDie(8090));

    final var json = json(
      field("id", "abd94004-6f46-4a12-b28d-ec9be66a6089")
    );

    bus.<JsonObject>localConsumer("any-action-command", it -> {
      it.reply(json.get());
      test.completeNow();
    });

    final var jsonString = jsonString(
      field("produced", 3000.0),
      field("consumed", 2000.0),
      field("putIn", 1000.0)
    );

    given()
      .port(8090)
      .contentType(JSON)
      .body(jsonString.get())
      .post("/api/apollo/{action}", "any-action")
    .then()
      .statusCode(200)
      .contentType(JSON)
      .body("actionId", matchesPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
      .body("actionId", is("abd94004-6f46-4a12-b28d-ec9be66a6089"));
  }
}
