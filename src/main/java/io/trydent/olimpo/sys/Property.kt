package io.trydent.olimpo.sys

interface Property : () -> String?

class EnvironmentVariable(private val name: String) : Property {
  override fun invoke(): String? = System.getenv(name)
}
