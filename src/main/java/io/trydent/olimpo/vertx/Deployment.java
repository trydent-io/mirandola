package io.trydent.olimpo.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.function.Consumer;

import static io.trydent.olimpo.type.When.when;
import static io.vertx.core.Vertx.vertx;
import static org.slf4j.LoggerFactory.getLogger;

@FunctionalInterface
public interface Deployment extends Consumer<Vertx> {
  static Verticles verticles(Deployment... deployments) {
    return new DeploymentVerticles(
      vertx(),
      Arrays.copyOf(deployments, deployments.length)
    );
  }

  interface Verticles extends Runnable {
    @Override
    default void run() {
      this.deploy();
    }

    void deploy();
  }

  final class DeploymentVerticles implements Verticles {
    private static final Logger log = getLogger(DeploymentVerticles.class);

    private final Vertx vertx;
    private final Deployment[] deployments;

    private DeploymentVerticles(final Vertx vertx, final Deployment[] deployments) {
      this.vertx = vertx;
      this.deployments = deployments;
    }

    @Override
    public final void deploy() {
      for (var deployment : deployments) {
        vertx.deployVerticle(new DeployedVerticle(deployment), async -> when(async.succeeded(),
          () -> log.info("Verticle ${service.javaClass.name} deployed."),
          () -> log.error("Verticle ${service.javaClass.name} not deployed: ${it.cause().message}.")));
      }
    }
  }
}

final class DeployedVerticle extends AbstractVerticle implements Verticle {
  private final Deployment deployment;

  DeployedVerticle(final Deployment deployment) {
    this.deployment = deployment;
  }

  @Override
  public final void start() {
    deployment.accept(vertx);
  }
}
