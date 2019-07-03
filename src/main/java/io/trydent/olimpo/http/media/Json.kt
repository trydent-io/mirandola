package io.trydent.olimpo.http.media

import io.vertx.core.json.JsonObject

fun json(vararg pairs: Pair<String, *>) = JsonObject.mapFrom(mapOf(*pairs))
