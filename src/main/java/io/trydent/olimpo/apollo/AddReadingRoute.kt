package io.trydent.olimpo.apollo

import io.trydent.olimpo.http.HttpExchange
import io.trydent.olimpo.http.HttpRoute
import io.trydent.olimpo.http.HttpValue.ApplicationJson
import io.trydent.olimpo.http.produces
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory.getLogger

class AddReadingRoute(private val path: String, private val exchange: HttpExchange) : HttpRoute {
  private val log = getLogger(javaClass)

  override fun invoke(router: Router) = router.apply {
    post(path)
      .produces(ApplicationJson)
      .handler(exchange())
    log.info("Add Reading http-command configured.")
  }
}
