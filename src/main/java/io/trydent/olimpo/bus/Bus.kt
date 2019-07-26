package io.trydent.olimpo.bus

interface Bus : () -> Unit {
  companion object {
    fun commandBus(process: Process, vararg commands: Command): Bus = CommandBus(process, arrayOf(*commands))
  }
}

internal class CommandBus(
  private val process: Process,
  private val commands: Array<Command>) : Bus {
  override fun invoke() = commands.forEach { it(process) }
}
