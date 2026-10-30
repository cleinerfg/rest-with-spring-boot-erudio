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
        logger.info("Finding all persons!");
        var persons = new ArrayList<Person>();

        for (int i = 0; i < 5; i++) {
            persons.add(mockPerson());
        }
        return persons;
    }

    public Person create(Person person) {
        logger.info("Creating person!");
        return person;
    }

    public void delete(Long id) {
        logger.info("Deleting person!");
    }

    public Person update(Person person) {
        logger.info("Updating person!");
        return person;
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
