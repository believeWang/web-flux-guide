package com.robert.webfluxguide;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Sinks;

class SinkTest {
  @Test
  void sinksTest() {
    Sinks.Many<Integer> replaySink = Sinks.many().replay().all();
    replaySink.emitNext(1, FAIL_FAST);
    replaySink.emitNext(2, FAIL_FAST);
    replaySink.asFlux().subscribe(t -> System.out.println("subscribe1 :" + t));
    replaySink.emitNext(3, FAIL_FAST);
    replaySink.emitNext(4, FAIL_FAST);
    replaySink.asFlux().subscribe(t -> System.out.println("subscribe2 :" + t));
    replaySink.emitNext(5, FAIL_FAST);
    /*
    subscribe1 :1
    subscribe1 :2
    subscribe1 :3
    subscribe1 :4
    subscribe2 :1
    subscribe2 :2
    subscribe2 :3
    subscribe2 :4
    subscribe1 :5
    subscribe2 :5
         */
  }

  @Test
  void sinksUniTest() {
    Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
    unicastSink.emitNext(1, FAIL_FAST);
    unicastSink.emitNext(2, FAIL_FAST);
    unicastSink.asFlux().subscribe(t -> System.out.println("subscribe1 :" + t));
    unicastSink.emitNext(3, FAIL_FAST);
    unicastSink.emitNext(4, FAIL_FAST);
    unicastSink.asFlux().subscribe(t -> System.out.println("subscribe2 :" + t));
    unicastSink.emitNext(5, FAIL_FAST);
    /*
    subscribe1 :1
    subscribe1 :2
    subscribe1 :3
    subscribe1 :4
    reactor.core.Exceptions$ErrorCallbackNotImplemented:
    java.lang.IllegalStateException: UnicastProcessor allows only a single Subscriber

         */
  }

  @Test
  void sinksMultiTest() {

    Sinks.Many<Integer> multicastSink = Sinks.many().multicast().onBackpressureBuffer();
    multicastSink.emitNext(1, FAIL_FAST);
    multicastSink.emitNext(2, FAIL_FAST);
    multicastSink.asFlux().subscribe(t -> System.out.println("subscribe1 :" + t));
    multicastSink.emitNext(3, FAIL_FAST);
    multicastSink.emitNext(4, FAIL_FAST);
    multicastSink.asFlux().subscribe(t -> System.out.println("subscribe2 :" + t));
    multicastSink.emitNext(5, FAIL_FAST);
    /*
    subscribe1 :1
    subscribe1 :2
    subscribe1 :3
    subscribe1 :4
    subscribe1 :5
    subscribe2 :5
         */
  }
}
