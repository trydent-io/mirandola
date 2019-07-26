package io.trydent.olimpo.bus

import io.trydent.olimpo.vertx.Json
import io.trydent.olimpo.bus.Execution as Execution

interface Command : (Process) -> Unit

private const val NAME = "add-reading-command"

internal class AddReading : Command {
  override fun invoke(process: Process) {
    process(NAME) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
  }
}
