package io.trydent.olimpo.bus

import io.trydent.olimpo.http.media.Json

interface CommandGateway {
  fun send(command: Command, params: Json): String
}
