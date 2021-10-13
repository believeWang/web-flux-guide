package com.robert.webfluxguide.config;

import com.robert.webfluxguide.consts.MessageType;
import io.r2dbc.spi.Row;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MessageTypeReadConverter
    implements org.springframework.core.convert.converter.Converter<String, MessageType> {

  @Override
  public MessageType convert(String source) {
    return MessageType.getTypeFromCode(source);
  }
}
