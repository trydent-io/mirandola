package io.trydent.olimpo.dispatch

import io.trydent.olimpo.vertx.Json

interface AsyncEvent : (String, Json) -> Unit
