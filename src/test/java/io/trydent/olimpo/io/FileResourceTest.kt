package io.trydent.olimpo.io

import io.vertx.core.Vertx.vertx
import io.vertx.core.buffer.Buffer.buffer
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test

internal class FileResourceTest {
  private val vertx = vertx()
  private val fileSystem = vertx.fileSystem()

  @Test
  internal fun `should read a file`() {
    val resource: FileResource = ResourceFile(file = "resource.file")

    assertThat(resource().result().toString().trim()).isEqualTo("nothing")
  }

  @Test
  internal fun `should read a file from file-system`() {
    val tmp = System.getProperty("java.io.tmpdir")
    val path = "$tmp/file.txt"
    fileSystem.writeFileBlocking(path, buffer("blabla"))
    val fsFile = FileSystemFile(fileSystem = fileSystem, file = path)

    val future = fsFile()
    await().until(future::isComplete)
    assertThat(future.result()).isEqualTo(buffer("blabla"))
  }
}
