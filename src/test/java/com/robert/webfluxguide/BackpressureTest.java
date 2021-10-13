package com.robert.webfluxguide;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class BackpressureTest {
  @Test
  void fluxWithoutBackpressure() {
    Flux.interval(Duration.ofMillis(1))
        .log()
        .concatMap(x -> Mono.delay(Duration.ofMillis(100)))
        .blockLast();
  }

  @Test
  void fluxWithBackpressure() {
    Flux.interval(Duration.ofMillis(1))
        .log()
        .onBackpressureBuffer()
        .concatMap(x -> Mono.delay(Duration.ofMillis(100)))
        .blockLast();
  }
  @Test
  void fluxWithBackpressure2() {

  }

  public static void main(String[] args) {
    Flux.range(1, 20)
        .log()
        .limitRate(3)
        .subscribe();
  }
}
