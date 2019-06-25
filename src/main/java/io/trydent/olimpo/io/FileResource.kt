package io.trydent.olimpo.io

import io.vertx.core.Future
import io.vertx.core.Future.failedFuture
import io.vertx.core.Future.future
import io.vertx.core.Future.succeededFuture
import io.vertx.core.buffer.Buffer
import io.vertx.core.buffer.Buffer.buffer
import io.vertx.core.file.FileSystem

interface FileResource : () -> Future<Buffer>

class ResourceFile(private val file: String) : FileResource {
  override fun invoke() = (succeededBuffer ?: failedBuffer)!!

  private val resourceAsInputStream get() = javaClass.classLoader.getResourceAsStream(file)
  private val failedBuffer get() = failedFuture<Buffer>("File not found.")
  private val succeededBuffer by lazy { succeededFuture(buffer(resourceAsInputStream?.readAllBytes())) }
}

class FileSystemFile(private val fileSystem: FileSystem, private val file: String) : FileResource {
  override fun invoke() = future<Buffer>().apply { fileSystem.readFile(file, this) }!!
}
