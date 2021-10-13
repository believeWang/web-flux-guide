package com.robert.webfluxguide;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import java.time.Duration;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class HotColdTest {
  @Test
  public void test() throws InterruptedException {

    Flux<Long> source = Flux.interval(Duration.ofSeconds(1));
    Flux<Long> clockTicks = source.share();
    clockTicks.subscribe(tick -> System.out.println("clock1 " + tick + "s"));
    Thread.sleep(2000);

    clockTicks.subscribe(tick -> System.out.println("\tclock2 " + tick + "s"));
    Thread.sleep(5000);
  }

  @Test
  public void testJust() throws InterruptedException {

    Mono<Long> clock = Mono.just(System.currentTimeMillis());
    Mono<Long> clockDefer = Mono.defer(() -> Mono.just(System.currentTimeMillis()));

    Thread.sleep(500);
    clock.subscribe(t -> System.out.println("clock:" + t));
    clockDefer.subscribe(t -> System.out.println("\tclockDefer:" + t));

    Thread.sleep(500);
    clock.subscribe(t -> System.out.println("clock:" + t));
    clockDefer.subscribe(t -> System.out.println("\tclockDefer:" + t));
  }

  @Test
  public void testReplay() throws InterruptedException {

    Flux<String> source =
        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
            .map(String::toUpperCase);

    source.subscribe(d -> System.out.println("Subscriber 1: " + d));
    source.subscribe(d -> System.out.println("Subscriber 2: " + d));
    /*
    Subscriber 1: BLUE
    Subscriber 1: GREEN
    Subscriber 1: ORANGE
    Subscriber 1: PURPLE
    Subscriber 2: BLUE
    Subscriber 2: GREEN
    Subscriber 2: ORANGE
    Subscriber 2: PURPLE
         */

    Sinks.Many<String> hotSource = Sinks.unsafe().many().multicast().directBestEffort();

    Flux<String> hotFlux = hotSource.asFlux().map(String::toUpperCase);

    hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: "+d));

    hotSource.emitNext("blue", FAIL_FAST);
    hotSource.tryEmitNext("green").orThrow();

    hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: "+d));

    hotSource.emitNext("orange", FAIL_FAST);
    hotSource.emitNext("purple", FAIL_FAST);
    hotSource.emitComplete(FAIL_FAST);
  }
}
