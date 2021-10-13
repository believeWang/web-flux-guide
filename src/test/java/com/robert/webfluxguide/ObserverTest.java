package com.robert.webfluxguide;

import com.robert.webfluxguide.observer.User;
import com.robert.webfluxguide.observer.YoutubeChannel;
import org.junit.jupiter.api.Test;

class ObserverTest {

  @Test
  void test() {
    User user = new User();
    YoutubeChannel cs50 = new YoutubeChannel("CS50");
    YoutubeChannel springDevelop = new YoutubeChannel("SpringDevelop");
    cs50.subscribe(user);
    springDevelop.subscribe(user);

    cs50.notifyObservers();
    springDevelop.notifyObservers();

    cs50.unsubscribe(user);

    cs50.notifyObservers();
    springDevelop.notifyObservers();

  }

}
