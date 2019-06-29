package io.trydent.olimpo.http.media

import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonObject

object Json {
  fun json(vararg pairs: Pair<String, *>): Buffer = JsonObject.mapFrom(mapOf(*pairs)).toBuffer()

  fun jsonAsString(vararg pairs: Pair<String, *>) = json(*pairs).toString()
}
