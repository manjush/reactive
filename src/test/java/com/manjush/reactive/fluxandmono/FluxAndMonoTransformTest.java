package com.manjush.reactive.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("manjush", "drisya", "mitra", "aswathy");

    @Test
    public void transformUsingMap() {

        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("MANJUSH", "DRISYA", "MITRA", "ASWATHY")
                .verifyComplete();


    }

    @Test
    public void transformUsingMap_Length() {

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(7, 6, 5, 7)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Repeat() {

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1) //to repeat flux one more time
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(7, 6, 5, 7, 7, 6, 5, 7)
                .verifyComplete();

    }
}
