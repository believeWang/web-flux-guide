package com.robert.webfluxguide;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class GenerateAndCreateTest {

  @Test
  void testSimpleGenerate() {
    Flux<String> flux =
        Flux.generate(
            () -> 0,
            (state, sink) -> {
              sink.next("3 x " + state + " = " + 3 * state);
              if (state == 5) sink.complete();
              return state + 1;
            });

    flux.subscribe(System.out::println);
    /*
    3 x 0 = 0
    3 x 1 = 3
    3 x 2 = 6
    3 x 3 = 9
    3 x 4 = 12
    3 x 5 = 15
         */

  }

  @Test
  void testMuteableState() throws InterruptedException {
    Flux<String> flux =
        Flux.generate(
            AtomicLong::new,
            (state, sink) -> {
              long i = state.getAndIncrement();
              sink.next("3 x " + i + " = " + 3 * i);
              sink.next("3 x " + i + " = " + 3 * i);
              if (i == 5) sink.complete();
              return state;
            },
            (state) -> System.out.println("state: " + state));
    flux.subscribe(System.out::println);
    /*
    3 x 0 = 0
    3 x 1 = 3
    3 x 2 = 6
    3 x 3 = 9
    3 x 4 = 12
    3 x 5 = 15
         */

  }
}
