package io.trydent.olimpo.io

import io.vertx.core.Vertx.vertx
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
    val path = "/home/lug/tmp/file.txt"
    val fsFile = FileSystemFile(fileSystem = fileSystem, file = path)

    val future = fsFile()
    await().until(future::isComplete)
    assertThat(future.result().toString().trim()).isEqualTo("blabla")
  }
}
