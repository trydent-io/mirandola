package io.trydent.olimpo.apollo

import io.trydent.olimpo.dispatch.Command
import io.trydent.olimpo.http.HttpExchange
import io.trydent.olimpo.http.HttpHeader.ContentType
import io.trydent.olimpo.http.HttpValue.ApplicationJson
import io.trydent.olimpo.http.end
import io.trydent.olimpo.http.headers
import io.trydent.olimpo.http.media.json
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AddReadingExchange(private val command: Command) : HttpExchange {
  override fun invoke() = Handler<RoutingContext> {
    command("", json())
    it.bodyAsJson.also { json ->
      it.response()
        .headers(
          ContentType to ApplicationJson
        )
        .end(
          json(
            "commandId" to "commandId"
          )
        )
    }
  }
}
