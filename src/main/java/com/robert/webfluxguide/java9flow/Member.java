package com.robert.webfluxguide.java9flow;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import lombok.Data;

@Data
public class Member <T>  implements Subscriber<T> {
  private Subscription subscription;
  private List<T> episodes = new LinkedList<>();

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onNext(T item) {
    System.out.println("onNext:" + item);
    episodes.add(item);
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
