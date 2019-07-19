package io.trydent.olimpo.vertx

import io.vertx.core.json.JsonObject

fun json(vararg fields: Pair<String, *>): JsonObject = JsonObject.mapFrom(mapOf(*fields))

operator fun JsonObject.plus(pair: Pair<String, *>): JsonObject = this.mergeIn(json(pair))

typealias Json = JsonObject
