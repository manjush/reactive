package com.manjush.reactive.fluxandmono;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                //  .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();
        // requires a subscriber
        stringFlux.subscribe(System.out::println,
                //handle error in imperative way
                (e) -> {
                    System.err.println("Exception is " + e);
                },
                () -> System.out.println("Completed")
        );

    }

    @Test
    public void fluxTestElements_withoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();// in test subscribe starts here
    }

    @Test
    public void fluxTestElements_withError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("exception occurred")
                .verify();
    }

    @Test
    public void fluxTestElements_withError_Simple() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("exception occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("exception occurred")
                .verify();
    }


}
