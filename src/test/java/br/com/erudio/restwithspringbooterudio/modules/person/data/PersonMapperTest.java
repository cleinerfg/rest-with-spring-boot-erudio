package br.com.erudio.restwithspringbooterudio.modules.person.data;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    private MockPerson input;

    private final PersonMapper mapper = new PersonMapper();

    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }

    @Test
    @DisplayName("Should successfully map a Person entity to PersonResponse")
    void shouldMapPersonToPersonResponseSuccessfully() {
        // Arrange
        Person mock = input.mockEntity(1);

        // Act
        PersonResponse response = mapper.toResponse(mock);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mock.getId());
        assertThat(response.getFirstName()).isEqualTo(mock.getFirstName());
        assertThat(response.getLastName()).isEqualTo(mock.getLastName());
        assertThat(response.getAddress()).isEqualTo(mock.getAddress());
        assertThat(response.getGender()).isEqualTo(mock.getGender());
    }

    @Test
    @DisplayName("Should successfully map a PersonRequest to a Person entity")
    void shouldMapPersonRequestToPersonEntitySuccessfully() {
        // Arrange
        PersonRequest request = input.mockRequest(1);

        // Act
        Person person = mapper.toEntity(request);

        // Assert
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNull();
        assertThat(person.getFirstName()).isEqualTo(request.firstName());
        assertThat(person.getLastName()).isEqualTo(request.lastName());
        assertThat(person.getAddress()).isEqualTo(request.address());
        assertThat(person.getGender()).isEqualTo(request.gender());
    }
}
