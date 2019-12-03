package com.manjush.reactive.initialize;

import com.manjush.reactive.document.Item;
import com.manjush.reactive.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
public class ItemDataInitializer implements CommandLineRunner{

    @Autowired
    private ItemReactiveRepository reactiveRepository;

    @Override
    public void run(String... args) throws Exception {
        initialDataSetUp();
    }


    public List<Item> data() {

        return Arrays.asList(new Item(null, "Samsung TV", 399.99),
                new Item(null, "LG TV", 329.99),
                new Item(null, "Apple Watch", 349.99),
                new Item("ABC", "Beats HeadPhones", 149.99));
    }


    private void initialDataSetUp() {
        reactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(reactiveRepository::save)
                .thenMany(reactiveRepository.findAll())
                .subscribe((item -> {
                    System.out.println("Item inserted from CommandLineRunner : " + item);
                }));

    }

}
