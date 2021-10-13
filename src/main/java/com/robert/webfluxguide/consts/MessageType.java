package com.robert.webfluxguide.consts;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum MessageType {
  VOICE("1"),
  MP3("2"),
  WORD("3")
  ;
  private String typeCode;

  MessageType(String typeCode) {
    this.typeCode = typeCode;
  }
  private static final Map<String, MessageType> BY_CODE = new HashMap<>(MessageType.values().length);
  static {
    for (MessageType e : MessageType.values()) {
      BY_CODE.put(e.getTypeCode(), e);
    }
  }
  public static MessageType getTypeFromCode(String typeCode) {
    return BY_CODE.getOrDefault(typeCode, WORD);
  }
}
