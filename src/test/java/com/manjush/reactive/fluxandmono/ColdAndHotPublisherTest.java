package com.manjush.reactive.fluxandmono;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B","C","D","E", "F")
                .delayElements(Duration.ofSeconds(1));
        stringFlux.subscribe(s -> log.info("Subscriber 1 : {}", s)); //emits the value from beginning

        Thread.sleep(2000);

        stringFlux.subscribe(s -> log.info("Subscriber 2 : {}", s)); //emits the value from beginning

        Thread.sleep(4000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B","C","D","E", "F")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> publish = stringFlux.publish();
        publish.connect();

        publish.subscribe((x) -> log.info("Subscriber 1 : ", x));

        Thread.sleep(3000);

        publish.subscribe((x) -> log.info("Subscriber 2 : ", x));//emits the same value



    }
}
