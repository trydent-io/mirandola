package io.trydent.olimpo.io

import io.trydent.olimpo.sys.Property

interface Port : (Int) -> Int {
  companion object {
    fun env(variable: String): Port = EnvPort(Property.env(variable))
  }
}

internal class EnvPort(private val property: Property) : Port {
  override fun invoke(default: Int): Int = try {
    Integer.parseInt(property())
  } catch (nfe: NumberFormatException) {
    default
  }

  override fun toString(): String = "${this(-1)}"
}

