package io.trydent.olimpo;

import static io.trydent.olimpo.vertx.Deployment.deploy;

public final class Olimpo {
  private Olimpo() {
  }

  public static void main(String... args) {
    deploy(
      vertx -> {
      },
      vertx -> {
      }
    ).run();
  }
}
