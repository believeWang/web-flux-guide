package com.robert.webfluxguide.vo;

import com.robert.webfluxguide.consts.MessageType;
import com.robert.webfluxguide.consts.YesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {
  @Id
  private Long id;
  private String message;
  private YesNo isSend;
  private MessageType messageType;

  public Greeting(String message) {
    this.message = message;
  }
  public Greeting(Long id, String message) {
    this.id = id;
    this.message = message;
  }

  public Greeting(String message, YesNo isSend,
      MessageType messageType) {
    this.message = message;
    this.isSend = isSend;
    this.messageType = messageType;
  }
}
