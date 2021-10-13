package com.robert.webfluxguide;

import java.time.Duration;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReactorTest {
  public <T> Flux<T> appendBoomError(Flux<T> source) {
    return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
  }
  @Test
  public void testAppendBoomError() {
    Flux<String> source = Flux.just("thing1", "thing2");

    StepVerifier.create(
            appendBoomError(source))
        .expectNext("thing1")
        .expectNext("thing2")
        .expectErrorMessage("boom")
        .verify();
  }

  @Test
  public void verify(){
    Flux<Integer> integers = Flux.range(3,7);

    StepVerifier.create(integers)
        .expectNextSequence(Arrays.asList(3,4,5,6,7))
        .expectNextMatches(d -> (d / 2 ) == 4)
        .expectNextCount(1)
        .verifyComplete();
  }
  @Test
  public void verifyWithVirtualTime(){
    StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
        .expectSubscription()
        .expectNoEvent(Duration.ofDays(1))
        .expectNext(0L)
        .verifyComplete();
  }


}
