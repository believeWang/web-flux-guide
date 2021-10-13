package com.robert.webfluxguide.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.robert.webfluxguide.repository.GreetingRepository;
import com.robert.webfluxguide.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GreetingHandler {

  private final GreetingRepository repository;

  public Mono<ServerResponse> hello(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(new Greeting(1L,"Hello, Spring!")));
  }
  public Mono<ServerResponse> getGreeting(ServerRequest request) {
    Long id = Long.parseLong(request.pathVariable("id"));
    Mono<Greeting> greetingMono = this.repository.findById(id);
    return greetingMono
        .flatMap(greeting -> ServerResponse.ok().contentType(APPLICATION_JSON).body(
            BodyInserters.fromValue(greeting)))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> saveGreeting(ServerRequest request) {
    Mono<Greeting> greetingMono = request.bodyToMono(Greeting.class);
    return ServerResponse.ok().build(this.repository.save(greetingMono));
  }

  public Mono<ServerResponse> allGreeting(ServerRequest request) {
    Flux<Greeting> greetingFlux = this.repository.findAll();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(greetingFlux, Greeting.class);
  }
}
