package com.robert.webfluxguide.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
@Configuration
public class AppConfig extends AbstractR2dbcConfiguration {

  @Override
  public ConnectionFactory connectionFactory() {
    return ConnectionFactories.get("r2dbc:mysql://localhost:3306/test");
  }

  @Override
  protected List<Object> getCustomConverters() {

    return List.of(
        new MessageTypeReadConverter(),
        new MessageTypeWriteConverter()
    );
  }
}
