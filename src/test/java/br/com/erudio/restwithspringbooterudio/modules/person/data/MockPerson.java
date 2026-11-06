package br.com.erudio.restwithspringbooterudio.modules.person.data;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonRequest mockRequest() {
        return mockRequest(0);
    }

    public Person mockEntity(Integer number) {
        var person = Person.builder()
                .firstName("Elena" + number)
                .lastName("Gilbert" + number)
                .address("Mystic Falls Boarding House" + number)
                .gender("Female")
                .enabled(true)
                .build();

        try {
            var idField = Person.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(person, number.longValue());
            return person;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public PersonRequest mockRequest(Integer number) {
        return PersonRequest.builder()
                .firstName("Elena" + number)
                .lastName("Gilbert" + number)
                .address("Mystic Falls Boarding House" + number)
                .gender("Female")
                .enabled(true)
                .build();
    }

    public PersonResponse mockResponse(Integer number) {
        return PersonResponse.builder()
                .id(number.longValue())
                .firstName("Elena" + number)
                .lastName("Gilbert" + number)
                .address("Mystic Falls Boarding House" + number)
                .gender("Female")
                .enabled(true)
                .build();
    }

    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }
}
