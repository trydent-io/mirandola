package io.trydent.olimpo.apollo

import io.trydent.olimpo.http.HttpRequest
import io.trydent.olimpo.http.HttpResource
import io.vertx.ext.web.Router

const val JSON = "application/json"
const val CONTENT_TYPE = "content-type"

class AddReadingRoute(private val path: String, private val request: HttpRequest) : HttpResource {
  override fun invoke(router: Router) = router.apply {
    post(path)
      .produces(JSON)
      .handler(request())
  }
}
