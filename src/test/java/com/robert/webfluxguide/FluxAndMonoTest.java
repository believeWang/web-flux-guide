package com.robert.webfluxguide;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

public class FluxAndMonoTest {

  @Test
  void test() {
    //    Flux<String> seq1 = Flux.just("Robert", "Jean", "Jerry");
    //    Flux<String> seq2 = Flux.fromIterable(Arrays.asList("Robert", "Jean", "Jerry"));
    //    Mono<String> mono = Mono.just("Robert");
    //    Mono<String> mono1 = Mono.empty();
    //    Mono<String> mono2 = Mono.justOrEmpty("Robert");
    //
    //    Flux<Integer> ints = Flux.range(1, 4);
    //    ints.map(String::valueOf).subscribe(System.out::println);
    //    Flux<String> strFlux = Flux.just("robert", "wang");
    //    strFlux.flatMap(i -> Flux.just(i.split(""))).subscribe(System.out::println);

    Flux<String> name = Flux.just("Robert", "Jean", "Jerry");
    Flux<Integer> age = Flux.just(30, 29, 36);
    Flux<String> sex = Flux.just("M", "F");
    Flux<Tuple3<String, Integer, String>> zip = Flux.zip(name, age, sex);
    zip.map(data -> new Customer(data.getT1(), data.getT2(), data.getT3()))
        .subscribe(System.out::println);
    //    Customer{name='Robert', age=30, sex='M'}
    //    Customer{name='Jean', age=29, sex='F'}
  }

  public static void main(String[] args) throws InterruptedException {


  }

  class Customer {
    private String name;
    private Integer age;
    private String sex;

    public Customer(String name, Integer age, String sex) {
      this.name = name;
      this.age = age;
      this.sex = sex;
    }

    @Override
    public String toString() {
      return "Customer{" + "name='" + name + '\'' + ", age=" + age + ", sex='" + sex + '\'' + '}';
    }
  }
}
