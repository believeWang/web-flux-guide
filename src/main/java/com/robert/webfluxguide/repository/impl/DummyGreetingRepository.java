package com.robert.webfluxguide.repository.impl;

import com.robert.webfluxguide.repository.GreetingRepository;
import com.robert.webfluxguide.vo.Greeting;
import java.util.HashMap;
import java.util.Map;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
public class DummyGreetingRepository {

  private final Map<Long, Greeting> greetings = new HashMap<>();

  public DummyGreetingRepository() {
    this.greetings.put(1L, new Greeting(1L,"Hello"));
    this.greetings.put(2L, new Greeting(2L,"World"));
  }


  public Mono<Greeting> findById(Long id) {
    return Mono.justOrEmpty(greetings.get(id));
  }

  public Flux<Greeting> findAll() {
    return Flux.fromIterable(greetings.values());
  }

  public Mono<Void> save(Mono<Greeting> greeting) {
    return greeting.doOnNext(person -> {
      Long id = (long) (greetings.size() + 1);
      greetings.put(id, person);
    }).thenEmpty(Mono.empty());
  }

}
