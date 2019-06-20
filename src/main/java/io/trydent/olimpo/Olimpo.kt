package io.trydent.olimpo

import io.trydent.olimpo.http.HttpServerVerticle
import io.trydent.olimpo.sys.Deployment
import io.trydent.olimpo.sys.VerticleDeployment
import io.vertx.core.Vertx.vertx

private val deployment: Deployment by lazy { VerticleDeployment(vertx()) }

fun main() = deployment(HttpServerVerticle())
