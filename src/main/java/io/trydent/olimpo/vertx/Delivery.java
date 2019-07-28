package io.trydent.olimpo.vertx;

import io.trydent.olimpo.type.Type;
import io.vertx.core.eventbus.DeliveryOptions;

import java.time.LocalDateTime;

public interface Delivery extends Type.As<DeliveryOptions> {
  static Delivery localDelivery() {
    return new LocalDelivery();
  }
}

final class LocalDelivery implements Delivery {
  @Override
  public final DeliveryOptions get() {
    return new DeliveryOptions()
      .addHeader("at", LocalDateTime.now().toString())
      .setLocalOnly(true);
  }
}
