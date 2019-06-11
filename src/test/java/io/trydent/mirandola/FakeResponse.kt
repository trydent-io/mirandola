package io.trydent.mirandola

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.MultiMap
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerResponse

abstract class FakeResponse : HttpServerResponse {
  override fun getStatusCode(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setWriteQueueMaxSize(maxSize: Int): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isChunked(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getStatusMessage(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun push(method: HttpMethod?, host: String?, path: String?, handler: Handler<AsyncResult<HttpServerResponse>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun push(method: HttpMethod?, path: String?, headers: MultiMap?, handler: Handler<AsyncResult<HttpServerResponse>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun push(method: HttpMethod?, path: String?, handler: Handler<AsyncResult<HttpServerResponse>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun push(method: HttpMethod?, host: String?, path: String?, headers: MultiMap?, handler: Handler<AsyncResult<HttpServerResponse>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun writeQueueFull(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(data: Buffer?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(data: Buffer?, handler: Handler<AsyncResult<Void>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(chunk: String?, enc: String?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(chunk: String?, enc: String?, handler: Handler<AsyncResult<Void>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(chunk: String?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun write(chunk: String?, handler: Handler<AsyncResult<Void>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun streamId(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun headWritten(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setStatusCode(statusCode: Int): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun endHandler(handler: Handler<Void>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun ended(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putHeader(name: String?, value: String?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putHeader(name: CharSequence?, value: CharSequence?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putHeader(name: String?, values: MutableIterable<String>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putHeader(name: CharSequence?, values: MutableIterable<CharSequence>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun writeContinue(): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putTrailer(name: String?, value: String?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putTrailer(name: CharSequence?, value: CharSequence?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putTrailer(name: String?, values: MutableIterable<String>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun putTrailer(name: CharSequence?, value: MutableIterable<CharSequence>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun bodyEndHandler(handler: Handler<Void>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun headersEndHandler(handler: Handler<Void>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun reset(code: Long) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun close() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun bytesWritten(): Long {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setChunked(chunked: Boolean): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: String?, handler: Handler<AsyncResult<Void>>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: String?, enc: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: String?, enc: String?, handler: Handler<AsyncResult<Void>>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: Buffer?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(chunk: Buffer?, handler: Handler<AsyncResult<Void>>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun end(handler: Handler<AsyncResult<Void>>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun writeCustomFrame(type: Int, flags: Int, payload: Buffer?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun sendFile(filename: String?, offset: Long, length: Long): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun sendFile(filename: String?, offset: Long, length: Long, resultHandler: Handler<AsyncResult<Void>>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setStatusMessage(statusMessage: String?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun closeHandler(handler: Handler<Void>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun closed(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun trailers(): MultiMap {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun drainHandler(handler: Handler<Void>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun headers(): MultiMap {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun exceptionHandler(handler: Handler<Throwable>?): HttpServerResponse {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
