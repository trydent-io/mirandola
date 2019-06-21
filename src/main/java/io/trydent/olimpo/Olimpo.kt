package io.trydent.olimpo

import io.trydent.olimpo.http.HttpServerVerticle
import io.trydent.olimpo.sys.Deploy
import io.trydent.olimpo.sys.VerticleDeploy
import io.vertx.core.Vertx.vertx

private val deploy: Deploy by lazy { VerticleDeploy(vertx()) }

fun main() = deploy(HttpServerVerticle())
