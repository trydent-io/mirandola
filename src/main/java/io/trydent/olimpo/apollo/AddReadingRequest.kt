package io.trydent.olimpo.apollo

import io.trydent.olimpo.http.HttpRequest
import io.trydent.olimpo.http.end
import io.trydent.olimpo.http.headers
import io.trydent.olimpo.http.media.json
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class AddReadingRequest : HttpRequest {
  override fun invoke() = Handler<RoutingContext> {
    it.response()
      .headers(
        CONTENT_TYPE to JSON
      )
      .end(
        json(
          "commandId" to "any"
        )
      )

  }
}
