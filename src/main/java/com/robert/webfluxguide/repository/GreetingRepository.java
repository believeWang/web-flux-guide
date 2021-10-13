package com.robert.webfluxguide.repository;

import com.robert.webfluxguide.vo.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GreetingRepository extends R2dbcRepository<Greeting, Long> {
  Mono<Greeting> findById(Long id);

  Flux<Greeting> findAll();
  Flux<Greeting> findByMessage(String message, Pageable pageable);
  Flux<Greeting> findByMessage(Publisher<String> message);
  Mono<Integer> deleteByMessage(String message);
  //Mono<Void> deleteByMessage(String message);
  //Mono<Boolean> deletePersonByLastname(String message);
  @Modifying
  @Query("UPDATE message SET message = :message where id = :id")
  Mono<Integer> updateMessage(String message, Long id);
  Mono<Void> save(Mono<Greeting> greeting);
}
