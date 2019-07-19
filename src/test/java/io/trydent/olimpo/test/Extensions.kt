package io.trydent.olimpo.test

import io.restassured.specification.RequestSpecification
import io.vertx.core.json.JsonObject
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
import org.hamcrest.core.Is

val String.isPresent: Matcher<String> get() = Is.`is`(this)

fun RequestSpecification.json(vararg fields: Pair<String, *>) =
  this.body(
    JsonObject.mapFrom(
      mapOf(*fields)
    ).toBuffer().bytes
  )

fun anyString(): Matcher<String> = any(String::class.java)
