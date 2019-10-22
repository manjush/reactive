package com.manjush.reactive.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    @Test
    public void combineUsingMerge() {
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergeFlux = flux1.mergeWith(flux2).log();

        StepVerifier.create(mergeFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_With_Delay() {
        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergeFlux = flux1.mergeWith(flux2).log();

        StepVerifier.create(mergeFlux)
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_Using_Concat() {
        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergeFlux = flux1.concatWith(flux2).log();

        StepVerifier.create(mergeFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    public void combineUsing_Zip() {
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergeFlux = Flux.zip(flux1, flux2,  (t1,t2) -> {
            return t1.concat(t2);
        }).log();

        StepVerifier.create(mergeFlux)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }
}
