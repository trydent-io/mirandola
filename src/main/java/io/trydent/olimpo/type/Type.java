package io.trydent.olimpo.type;

import java.util.function.Supplier;

public interface Type {
  interface As<T> extends Supplier<T> {}

  interface AsString extends Supplier<String>{}

  interface AsInt {
    int get();
  }

  interface AsByte {
    byte get();
  }
}
