package io.trydent.olimpo.type;

public interface When {
  static void when(boolean value, Runnable then, Runnable otherwise) {
    if (value)
      then.run();
    else
      otherwise.run();
  }
}
