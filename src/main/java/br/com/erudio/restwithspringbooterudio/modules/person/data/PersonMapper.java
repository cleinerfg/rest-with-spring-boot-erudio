package br.com.erudio.restwithspringbooterudio.modules.person.data;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonMapper {

    public static PersonResponse toResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthDate(LocalDate.now(ZoneId.of("UTC")))
                .address(person.getAddress())
                .gender(person.getGender())
                .build();
    }

    public static Person toEntity(PersonRequest request) {
        return Person.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .address(request.address())
                .gender(request.gender())
                .build();
    }
}
