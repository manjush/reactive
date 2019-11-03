package com.manjush.reactive.fluxandmono;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest() {
        Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();
        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressure() {
        Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();

        finiteFlux.subscribe((el) -> log.info("Element is : {} ",el)
        , (e) ->log.info("Exception is {}", e)
        , () -> log.info("done")
        , (subscription -> subscription.cancel()));


    }

    @Test
    public void customised_backPressure() {
        Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();
        finiteFlux.subscribe(new BaseSubscriber<Integer>() {

            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                log.info("value is : {}", value);
                if (value == 4) {
                    cancel();
                }
            }
        });
    }
}
