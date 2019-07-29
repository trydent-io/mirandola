package io.trydent.olimpo.io;

import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.vertx.core.buffer.Buffer.buffer;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
class FileResourceTest {
  private final Vertx vertx;
  private final FileSystem fileSystem;

  FileResourceTest(Vertx vertx) {
    this.vertx = vertx;
    this.fileSystem = vertx.fileSystem();
  }

  @Test
  @DisplayName("should read a file from resources folder")
  void shouldReadFileFromResources() {
    final var resource = new ResourceFile("resource.file");

    assertThat(resource.get().future().result()).isEqualTo(buffer("nothing\n"));
  }

  /*
  @Test
  internal fun `should read a file from file-system`() {
    val tmp = System.getProperty("java.io.tmpdir")
    val path = "$tmp/file.txt"
    fileSystem.writeFileBlocking(path, buffer("blabla"))
    val fsFile = FileSystemFile(fileSystem = fileSystem, path = path)

    val future = fsFile()
    await().until(future::isComplete)
    assertThat(future.result()).isEqualTo(buffer("blabla"))
  }*/
}
