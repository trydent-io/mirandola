package io.trydent.olimpo.sys

import java.util.UUID.randomUUID

interface Id : () -> String {
  companion object {
    fun uuid(): Id = RandomUuid()

    fun id(value: String): Id = PlainId(value)
  }
}

internal class RandomUuid : Id {
  override fun invoke(): String = "${randomUUID()}"
}

internal class PlainId(private val value: String) : Id {
  override fun invoke() = value
}


