package com.robert.webfluxguide.controller;

import com.robert.webfluxguide.repository.GreetingRepository;
import com.robert.webfluxguide.vo.Greeting;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mvc/greeting")
@RequiredArgsConstructor
public class GreetingController {

	private final GreetingRepository repository;


	@GetMapping
	public Flux<Greeting> allPeople() {
		return this.repository.findAll();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> saveGreeting(@RequestBody Mono<Greeting> greetingMono) {
		return this.repository.save(greetingMono);
	}

	@GetMapping("/{id}")
	public Mono<Greeting> getGreeting(@PathVariable Long id) {
		return this.repository.findById(id);
	}

}
