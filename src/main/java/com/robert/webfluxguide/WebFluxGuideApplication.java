package com.robert.webfluxguide;

import com.robert.webfluxguide.client.GreetingClient;
import com.robert.webfluxguide.consts.MessageType;
import com.robert.webfluxguide.consts.YesNo;
import com.robert.webfluxguide.repository.GreetingRepository;
import com.robert.webfluxguide.vo.Greeting;
import io.r2dbc.spi.ConnectionFactory;
import java.time.Duration;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
@Slf4j
public class WebFluxGuideApplication {
  private static GreetingClient greetingClient;

  public WebFluxGuideApplication(GreetingClient greetingClient) {
    this.greetingClient = greetingClient;
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        SpringApplication.run(WebFluxGuideApplication.class, args);
    //		GreetingClient greetingClient = context.getBean(GreetingClient.class);
    // We need to block for the content here or the JVM might exit before the message is logged
    System.out.println(">> message = " + greetingClient.getMessage().block());
  }

  @Bean
  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

    return initializer;
  }

  @Bean
  public CommandLineRunner demo(GreetingRepository repository) {

    return (args) -> {
      // save a few Greeting
      repository
          .saveAll(
              Arrays.asList(
                  new Greeting("Hello", YesNo.N, MessageType.WORD),
                  new Greeting("Hello", YesNo.N, MessageType.MP3),
                  new Greeting("Yo", YesNo.Y, MessageType.WORD),
                  new Greeting("Nice to meet you", YesNo.Y, MessageType.WORD),
                  new Greeting("Hi", YesNo.N, MessageType.WORD)))
          .blockLast(Duration.ofSeconds(10));

      // fetch all Greeting
      log.info("Greeting found with findAll():");
      log.info("-------------------------------");
      repository
          .findAll()
          .doOnNext(
              greeting -> {
                log.info(greeting.toString());
              })
          .blockLast(Duration.ofSeconds(10));

      log.info("");

      // fetch an individual Greeting by ID
      repository
          .findById(1L)
          .doOnNext(
              greeting -> {
                log.info("Greeting found with findById(1L):");
                log.info("--------------------------------");
                log.info(greeting.toString());
                log.info("");
              })
          .block(Duration.ofSeconds(10));

      // fetch Greeting by last name
      log.info("Greeting found with findByMessage('Hello'):");
      log.info("--------------------------------------------");
      repository
          .findByMessage("Hello", PageRequest.of(0 ,10))
          .take(2)
          .doOnNext(
              hello -> {
                log.info(hello.toString());
              })
          .blockLast(Duration.ofSeconds(10));
      log.info("");
    };
  }
}
