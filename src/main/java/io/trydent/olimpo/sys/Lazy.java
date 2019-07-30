package io.trydent.olimpo.sys;

import java.util.function.Supplier;

import static io.trydent.olimpo.sys.Emptyness.None;

public interface Lazy<T> extends Supplier<T> {
  static <V> Lazy<V> lazy(final Supplier<V> initialization) {
    return new SyncLazy<>(initialization, None);
  }
}

enum Emptyness { None }

final class SyncLazy<T> implements Lazy<T> {
  private final Supplier<T> initialization;
  private volatile Object value;

  SyncLazy(final Supplier<T> initialization, final Object initial) {
    this.initialization = initialization;
    this.value = initial;
  }

  @Override
  @SuppressWarnings("unchecked")
  public final T get() {
    final var v1 = value;
    if (!v1.equals(None))
      return (T) v1;
    else synchronized (this) {
      final var v2 = value;
      return !v2.equals(None) ? (T) v1 : initialize();
    }
  }

  private T initialize() {
    final var initializated = initialization.get();
    this.value = initializated;
    return initializated;
  }
}
