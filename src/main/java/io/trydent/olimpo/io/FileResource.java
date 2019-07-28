package io.trydent.olimpo.io;

import io.trydent.olimpo.type.Type;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

import static java.util.Objects.nonNull;

public interface FileResource extends Type.As<Promise<Buffer>> {

}

final class ResourceFile implements FileResource {
  private final String file;

  ResourceFile(final String file) {
    this.file = file;
  }

  @Override
  public final Promise<Buffer> get() {
    try {
      final var input = this
        .getClass()
        .getClassLoader()
        .getResourceAsStream(file);

      if (nonNull(input)) {
        return Promise.succeededPromise(Buffer.buffer(input.readAllBytes()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Promise.failedPromise("");
  }
}
/*

class FileSystemFile(private val fileSystem: FileSystem, private val path: String) : FileResource {
  override fun invoke(): Future<Buffer> =
    future<Buffer>().apply {
      fileSystem.readFile(path, this)
    }
}
*/
