package br.com.erudio.restwithspringbooterudio.service;

import br.com.erudio.restwithspringbooterudio.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info("Finding person by id!");

        return Person.builder()
                .id(counter.incrementAndGet())
                .firstName("Jeremy")
                .lastName("Guilbert")
                .address("Mystic Falls - EUA")
                .gender("Male")
                .build();
    }

    public List<Person> findAll() {
        var persons = new ArrayList<Person>();

        for (int i = 0; i < 5; i++) {
            persons.add(mockPerson());
        }
        return persons;
    }

    private Person mockPerson() {
        return Person.builder()
                .id(counter.incrementAndGet())
                .firstName("FirstName " + counter.incrementAndGet())
                .lastName("LasName")
                .address("Some address")
                .gender("Male")
                .build();
    }
}
