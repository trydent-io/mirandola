package io.trydent.olimpo.io

import io.vertx.core.Future
import io.vertx.core.Future.failedFuture
import io.vertx.core.Future.future
import io.vertx.core.Future.succeededFuture
import io.vertx.core.buffer.Buffer
import io.vertx.core.buffer.Buffer.buffer
import io.vertx.core.file.FileSystem
import java.io.InputStream

interface FileResource : () -> Future<Buffer>

fun String.asResource(): InputStream? = javaClass.classLoader.getResourceAsStream(this)
fun InputStream.asBuffer(): Buffer = buffer(this.readAllBytes())

class ResourceFile(private val file: String) : FileResource {
  override fun invoke():Future<Buffer> = (succeededBuffer ?: failedBuffer)!!

  private val failedBuffer get() = failedFuture<Buffer>("File not found.")
  private val succeededBuffer by lazy { succeededFuture(file.asResource()?.asBuffer()!!) }
}

class FileSystemFile(private val fileSystem: FileSystem, private val path: String) : FileResource {
  override fun invoke() = future<Buffer>().apply { fileSystem.readFile(path, this) }!!
}
