package io.trydent.olimpo.io

import io.vertx.core.Future
import io.vertx.core.Future.failedFuture
import io.vertx.core.Future.succeededFuture
import io.vertx.core.buffer.Buffer
import io.vertx.core.buffer.Buffer.buffer

interface FileResource : () -> Future<Buffer>

class ResourceFile(private val file: String) : FileResource {
  override fun invoke() = (succeededBuffer ?: failedBuffer)!!

  private val resourceAsInputStream get() = javaClass.classLoader.getResourceAsStream(file)
  private val failedBuffer get() = failedFuture<Buffer>("File not found.")
  private val succeededBuffer by lazy { succeededFuture(buffer(resourceAsInputStream?.readAllBytes())) }
}
