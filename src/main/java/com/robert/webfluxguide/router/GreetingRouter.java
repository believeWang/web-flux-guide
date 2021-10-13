package com.robert.webfluxguide.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.robert.webfluxguide.handler.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

  @Bean
  public RouterFunction<ServerResponse> routeHello(GreetingHandler greetingHandler) {

    return RouterFunctions.route(
        GET("/hello").and(accept(APPLICATION_JSON)), greetingHandler::hello);
  }


  @Bean
  public RouterFunction<ServerResponse> routerFunction(GreetingHandler handler) {
    return nest(
        path("/greeting"),
        nest(
                accept(APPLICATION_JSON),
                route(GET("/{id}"), handler::getGreeting)
                    .andRoute(method(HttpMethod.GET), handler::allGreeting))
            .andRoute(POST("/").and(contentType(APPLICATION_JSON)), handler::saveGreeting));
  }
}
