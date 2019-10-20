package com.manjush.reactive.fluxandmono;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")));
        // requires a subscriber
        stringFlux.subscribe(System.out::println,
                //handle error in imperative way
                (e)-> {System.err.println(e);});

    }




}
