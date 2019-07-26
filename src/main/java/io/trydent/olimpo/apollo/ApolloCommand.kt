package io.trydent.olimpo.apollo

import io.trydent.olimpo.bus.Command
import io.trydent.olimpo.bus.Process
import org.slf4j.LoggerFactory.getLogger

interface ApolloCommand : Command {
  companion object {
    fun addReading(): Command = AddReading()
  }
}

private const val ADD_READING = "add-reading-command"

internal class AddReading : Command {
  private val log = getLogger(javaClass)

  override fun invoke(process: Process) {
    process(ADD_READING) {
      log.info("Add reading: $it")
    }
  }
}
