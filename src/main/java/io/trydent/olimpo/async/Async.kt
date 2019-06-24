package io.trydent.olimpo.async

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Future.future
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer

interface Async<T> : () -> T

fun <T> async(promise: Handler<AsyncResult<T>>) {
  val future = future<T>()

}

fun read(vertx: Vertx): Future<Buffer> {
  val future = future<Buffer>()
  vertx.fileSystem().readFile("/") {
    if (it.succeeded()) {
      future.complete(it.result())
    } else {
      future.fail(it.cause())
    }
  }
  return future
}

fun readS(vertx: Vertx) {
  read(vertx).result()
}
