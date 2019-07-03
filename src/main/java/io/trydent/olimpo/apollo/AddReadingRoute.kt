package io.trydent.olimpo.apollo

import io.trydent.olimpo.http.HttpExchange
import io.trydent.olimpo.http.HttpRoute
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory.getLogger

const val JSON = "application/json"
const val CONTENT_TYPE = "content-type"

class AddReadingRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  private val log = getLogger(javaClass)

  override fun invoke(router: Router) = router.apply {
    post(path)
      .produces(JSON)
      .handler(BodyHandler.create())
      .handler(exchange())
    log.info("Add Reading http-command configured.")
  }
}
