package br.com.erudio.restwithspringbooterudio.modules.person.data;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonResponse toResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .gender(person.getGender())
                .build();
    }

    public Person toEntity(PersonRequest request) {
        return Person.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(request.address())
                .gender(request.gender())
                .build();
    }
}
