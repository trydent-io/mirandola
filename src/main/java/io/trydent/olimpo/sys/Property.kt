package io.trydent.olimpo.sys

interface Property : () -> String? {
  companion object {
    fun env(variable: String): Property = EnvVar(variable)

    fun system(property: String): Property = SystemProperty(property)
  }
}

internal class EnvVar(private val name: String) : Property {
  override fun invoke(): String? = System.getenv(name)
}

internal class SystemProperty(private val name: String) : Property {
  override fun invoke(): String? = System.getProperty(name)
}
