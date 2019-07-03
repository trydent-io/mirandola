package io.trydent.olimpo.apollo

import io.trydent.olimpo.apollo.ApolloCommand.AddReading
import io.trydent.olimpo.bus.CommandGateway
import io.trydent.olimpo.http.HttpExchange
import io.trydent.olimpo.http.end
import io.trydent.olimpo.http.headers
import io.trydent.olimpo.http.media.json
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AddReadingExchange(private val commands: CommandGateway) : HttpExchange {
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
