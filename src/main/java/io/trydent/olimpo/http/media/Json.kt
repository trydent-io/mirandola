package io.trydent.olimpo.http.media

import io.vertx.core.json.JsonObject

fun json(vararg pairs: Pair<String, *>): JsonObject = JsonObject.mapFrom(mapOf(*pairs))

operator fun JsonObject.plus(pair: Pair<String, *>): JsonObject = this.mergeIn(json(pair))

typealias Json = JsonObject
