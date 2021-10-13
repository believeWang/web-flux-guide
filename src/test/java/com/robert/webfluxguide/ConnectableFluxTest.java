package com.robert.webfluxguide;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ConnectableFluxTest {

  @Test
  void test() throws InterruptedException {
    Flux<Integer> source =
        Flux.range(1, 5)
            .doOnSubscribe(s -> System.out.println("subscribed to source"));

    ConnectableFlux<Integer> co = source.replay(2);

    co.subscribe(System.out::println, e -> {}, () -> {});

    System.out.println("done subscribing");
    Thread.sleep(2000);

    System.out.println("will now connect");
    co.connect();

    co.subscribe(System.out::println, e -> {}, () -> {});
  }
}
