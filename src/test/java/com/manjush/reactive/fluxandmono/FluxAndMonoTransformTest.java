package com.manjush.reactive.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

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

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap( s -> {
                    return Flux.fromIterable(convertToList(s));
                }).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapUsingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2) // Flux<Flux<String>> -> (A,B) (C,D)..
                .flatMap( (s) ->
                    s.map(this::convertToList).subscribeOn(parallel())) //Flux<List<String>>
                            .flatMap(a -> Flux.fromIterable(a)).log();// Flux<String>


        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapUsingParallel_Maintain_Order() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2) // Flux<Flux<String>> -> (A,B) (C,D)..
                /*.concatMap( (s) ->
                        s.map(this::convertToList).subscribeOn(parallel())) //Flux<List<String>>*/
                .flatMapSequential( (s) ->
                        s.map(this::convertToList).subscribeOn(parallel())) //Flux<List<String>>
                .flatMap(a -> Flux.fromIterable(a)).log();// Flux<String>


        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s,"newValue");
    }
}
