package io.trydent.olimpo.apollo

import java.util.UUID.randomUUID

interface Id : () -> String {
  companion object {
    fun uuid(): Id = Uuid()
  }
}

internal class Uuid : Id {
  override fun invoke(): String = "${randomUUID()}"
}


