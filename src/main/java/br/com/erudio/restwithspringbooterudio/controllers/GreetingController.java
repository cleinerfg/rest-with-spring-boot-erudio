package br.com.erudio.restwithspringbooterudio.controllers;

import br.com.erudio.restwithspringbooterudio.models.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // http://localhost:8080/greeting?name=Cleiner
    @RequestMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World")
            String name) {
        return Greeting.builder()
                .id(counter.incrementAndGet())
                .content(String.format(TEMPLATE, name))
                .build();
    }
}
