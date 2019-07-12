package io.trydent.olimpo.sys

interface Property : () -> String?

class EnvVar(private val name: String) : Property {
  override fun invoke(): String? = System.getenv(name)
}

class SystemProperty(private val name: String) : Property {
  override fun invoke(): String? = System.getProperty(name)
}
