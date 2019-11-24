package com.manjush.reactive.repository;

import com.manjush.reactive.document.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {
    Mono<Item> findByDescription(String description);
}
