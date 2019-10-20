package com.manjush.reactive.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("manjush", "drisya", "mitra", "aswathy");


    @Test
    public void fluxAndFilterTest() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(n -> n.startsWith("m"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("manjush", "mitra")
                .verifyComplete();
    }
}
