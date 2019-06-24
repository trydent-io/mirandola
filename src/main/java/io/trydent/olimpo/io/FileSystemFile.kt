package io.trydent.olimpo.io

import io.vertx.core.Future.future
import io.vertx.core.buffer.Buffer
import io.vertx.core.file.FileSystem

class FileSystemFile(private val fileSystem: FileSystem, private val file: String) : FileResource {
  override fun invoke() = future<Buffer>().apply { fileSystem.readFile(file, this) }!!
}
