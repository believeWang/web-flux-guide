package com.robert.webfluxguide.config;

import com.robert.webfluxguide.consts.MessageType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class MessageTypeWriteConverter implements Converter<MessageType, String> {

  @Override
  public String convert(MessageType source) {
    return source.getTypeCode();
  }
}
