package com.robert.webfluxguide.observer;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeChannel implements Subject{

  private String name;
  private List<Observer> users;

  public YoutubeChannel(String name) {
    this.name = name;
    users  = new ArrayList<>();
  }

  @Override
  public void subscribe(Observer observer) {
    users.add(observer);
  }

  @Override
  public void unsubscribe(Observer observer) {
    users.remove(observer);
  }

  @Override
  public void notifyObservers() {
    users.forEach(user -> user.update(name));
  }
}
