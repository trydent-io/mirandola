package io.trydent.olimpo.apollo

import io.trydent.olimpo.apollo.ApolloCommand.AddReading
import io.trydent.olimpo.bus.CommandGateway
import io.trydent.olimpo.http.HttpRequest
import io.trydent.olimpo.http.end
import io.trydent.olimpo.http.headers
import io.trydent.olimpo.http.media.json
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AddReadingRequest(private val commands: CommandGateway) : HttpRequest {
  override fun invoke() = Handler<RoutingContext> {
    it.bodyAsJson.also { json ->
      it.response()
        .headers(
          CONTENT_TYPE to JSON
        )
        .end(
          json(
            "commandId" to commands.send(AddReading, json)
          )
        )
    }
  }
}
