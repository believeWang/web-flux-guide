package com.robert.webfluxguide;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.scheduler.VirtualTimeScheduler;

public class SchedulerTest {

  @Test
  void testVirtualTimeScheduler() throws InterruptedException {
    List<Long> list = new ArrayList<>();

    VirtualTimeScheduler scheduler = VirtualTimeScheduler.getOrSet();
    Flux.interval(Duration.ofSeconds(10), Duration.ofSeconds(5), scheduler)
        .take(3)
        .subscribe(list::add);

    scheduler.advanceTimeBy(Duration.ofSeconds(10));
    System.out.println(list);
    scheduler.advanceTimeBy(Duration.ofSeconds(5));
    System.out.println(list);
    scheduler.advanceTimeBy(Duration.ofSeconds(5));
    System.out.println(list);
  }

  @Test
  void test() throws InterruptedException {
    Flux.just("hello")
        .doOnNext(v -> System.out.println("just " + Thread.currentThread().getName()))
        .publishOn(Schedulers.boundedElastic())
        .doOnNext(v -> System.out.println("publish " + Thread.currentThread().getName()))
        .subscribe(v -> System.out.println(v + " delayed " + Thread.currentThread().getName()));
  }

  @Test
  void testDefaultScheduler() throws InterruptedException {
    List<Long> list = new ArrayList<>();

    Flux.interval(Duration.ofSeconds(10), Duration.ofSeconds(5)).take(3).subscribe(list::add);

    Thread.sleep(10500);
    System.out.println(list);
    Thread.sleep(5000);
    System.out.println(list);
    Thread.sleep(5000);
    System.out.println(list);
  }

  public static void main(String[] args) throws InterruptedException {

    Flux.just("hello")
        .doOnNext(v -> System.out.println("just " + Thread.currentThread().getName()))
        .publishOn(Schedulers.boundedElastic())
        .doOnNext(v -> System.out.println("publish " + Thread.currentThread().getName()))
        .publishOn(Schedulers.immediate())
        .subscribe(v -> System.out.println(v + " delayed " + Thread.currentThread().getName()));

    Thread.sleep(500);
  }
}
