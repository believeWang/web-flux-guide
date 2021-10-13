package com.robert.webfluxguide.observer;

public class User implements Observer{

  @Override
  public void update(String name) {
    System.out.println(name + "更新囉");
  }
}
