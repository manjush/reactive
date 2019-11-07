package com.manjush.reactive.router;

import com.manjush.reactive.handler.SampleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> route(SampleHandler handler) {
        return RouterFunctions.route((GET("/functional/flux").and(accept(MediaType.APPLICATION_JSON))), handler::flux)
               .andRoute((GET("/functional/mono").and(accept(MediaType.APPLICATION_JSON))), handler::mono);
    }
}
