package br.com.erudio.restwithspringbooterudio.modules.person.data;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    @Test
    @DisplayName("Should successfully map a Person entity to PersonResponse")
    void shouldMapPersonToPersonResponseSuccessfully() {
        // Arrange
        Person mock = mockPersonEntity();
        
        //Act
        PersonResponse response = PersonMapper.toResponse(mock);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(mock.getId());
        assertThat(response.firstName()).isEqualTo(mock.getFirstName());
        assertThat(response.lastName()).isEqualTo(mock.getLastName());
        assertThat(response.address()).isEqualTo(mock.getAddress());
        assertThat(response.gender()).isEqualTo(mock.getGender());
    }

    @Test
    @DisplayName("Should successfully map a PersonRequest to a Person entity")
    void shouldMapPersonRequestToPersonEntitySuccessfully() {
        // Arrange
        PersonRequest request = mockPersonRequest();

        // Act
        Person person = PersonMapper.toEntity(request);

        // Assert
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNull();
        assertThat(person.getFirstName()).isEqualTo(request.firstName());
        assertThat(person.getLastName()).isEqualTo(request.lastName());
        assertThat(person.getAddress()).isEqualTo(request.address());
        assertThat(person.getGender()).isEqualTo(request.gender());
    }

    private static Person mockPersonEntity() {
        var person = Person.builder()
                .firstName("Jeremy")
                .lastName("Gilbert")
                .address("Mystic Falls")
                .gender("Male")
                .build();

        try {
            var idField = Person.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(person, 1001L);
            return person;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static PersonRequest mockPersonRequest() {
        return PersonRequest.builder()
                .firstName("Elena")
                .lastName("Gilbert")
                .address("Mystic Falls Boarding House")
                .gender("Female")
                .build();
    }
}
