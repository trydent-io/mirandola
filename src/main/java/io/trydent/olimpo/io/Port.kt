package io.trydent.olimpo.io

import io.trydent.olimpo.sys.Property
import io.trydent.olimpo.sys.Property.Companion.envVar

@Suppress("MemberVisibilityCanBePrivate")
interface Port : () -> Int {
  companion object {
    fun envPort(name: String, default: Int): Port = EnvPort(envVar(name), port(default))

    fun port(value: Int): Port? = value
      .takeIf { it > 0 }
      ?.let(::PortImpl)
  }
}

internal class PortImpl(private val port: Int) : Port {
  override fun invoke(): Int = port

  override fun toString() = "${this()}"
}

internal class EnvPort(private val property: Property, private val default: Port?) : Port {
  override fun invoke(): Int = try {
    Integer.parseInt(property())
  } catch (nfe: NumberFormatException) {
    default!!()
  }

  override fun toString() = "${this()}"
}

