package com.manjush.reactive.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("manjush", "drisya", "mitra", "aswathy");

    @Test
    public void fluxUsingIterable() {
        Flux<String> stringFlux = Flux.fromIterable(names).log();

        StepVerifier.create(stringFlux)
                .expectNext("manjush", "drisya", "mitra", "aswathy")
                .verifyComplete();

    }

    @Test
    public void fluxUsingArray() {
        String[] names = {"manjush", "drisya", "mitra", "aswathy"};
        Flux<String> stringFlux = Flux.fromArray(names);

        StepVerifier.create(stringFlux)
                .expectNext("manjush", "drisya", "mitra", "aswathy")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        Flux<String> stringFlux = Flux.fromStream(names.stream()).log();

        StepVerifier.create(stringFlux)
                .expectNext("manjush", "drisya", "mitra", "aswathy")
                .verifyComplete();

    }

    @Test
    public void monoUsingJustOrEmpty() {

        Mono<Object> objectMono = Mono.justOrEmpty(null);
        StepVerifier.create(objectMono)
                .expectComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "adam";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier).log();

        StepVerifier.create(stringMono)
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> range = Flux.range(1, 5).log();

        StepVerifier.create(range)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

}
