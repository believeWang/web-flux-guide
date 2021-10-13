package com.robert.webfluxguide.java9flow;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplePodcastProcessor<T, R> extends SubmissionPublisher<R> implements Processor<T, R> {
  private Subscription subscription;
  private Function<T, R> function;

  public ApplePodcastProcessor(Function<T, R> function) {
    super();
    this.function = function;
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onNext(T item) {
    submit(function.apply(item));
    subscription.request(1);
  }

  @Override
  public void onError(Throwable throwable) {
    System.out.println("onError:" + throwable);
  }

  @Override
  public void onComplete() {
    System.out.println("onComplete");
  }
}
