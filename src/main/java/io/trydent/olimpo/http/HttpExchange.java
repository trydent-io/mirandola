package io.trydent.olimpo.http;

import io.trydent.olimpo.action.Action;
import io.trydent.olimpo.type.Type;
import io.trydent.olimpo.vertx.json.Json;
import io.trydent.olimpo.vertx.json.JsonBuffer;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import org.slf4j.Logger;

import static io.trydent.olimpo.http.HttpHeader.Name.ContentType;
import static io.trydent.olimpo.http.HttpHeader.Value.ApplicationJson;
import static io.trydent.olimpo.vertx.json.Json.Field.field;
import static io.trydent.olimpo.vertx.json.Json.json;
import static io.trydent.olimpo.vertx.json.JsonBuffer.jsonBuffer;
import static java.util.Arrays.copyOf;
import static org.slf4j.LoggerFactory.getLogger;

public interface HttpExchange extends Type.As<Handler<RoutingContext>> {
  static HttpExchange staticContent(String folder) {
    return new StaticContent(folder);
  }

  static HttpExchange actionSwitch(Action... actions) {
    return new ActionSwitch(copyOf(actions, actions.length));
  }
}

final class StaticContent implements HttpExchange {
  private final String folder;

  StaticContent(final String folder) {
    this.folder = folder;
  }

  @Override
  public final Handler<RoutingContext> get() {
    return StaticHandler.create(folder);
  }
}

final class ActionSwitch implements HttpExchange {
  private static final Logger log = getLogger(ActionSwitch.class);

  private final Action[] actions;

  ActionSwitch(final Action[] actions) {
    this.actions = actions;
  }

  @Override
  public final Handler<RoutingContext> get() {
    return routing -> routing.request().bodyHandler(buffer -> {
      for (var action : actions) asyncResponse(routing, action, json(buffer));
    });
  }

  private void asyncResponse(RoutingContext routing, Action action, Json json) {
    action
      .apply(routing.pathParam("action"), json)
      .future()
      .setHandler(async ->
        response(
          routing,
          jsonBuffer(
            field("actionId", async.result().get())
          )
        )
      );
  }

  private void response(RoutingContext routing, JsonBuffer json) {
    routing
      .response()
      .putHeader(ContentType, ApplicationJson)
      .end(json.get());
  }
}
