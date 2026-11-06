package br.com.erudio.restwithspringbooterudio.integration.controllers;

import br.com.erudio.restwithspringbooterudio.config.TestConfig;
import br.com.erudio.restwithspringbooterudio.integration.AbstractIntegrationTest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.MockPerson;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static final MockPerson input = new MockPerson();

    @BeforeAll
    static void setUp() {
        // Ignores extra JSON fields (HATEOAS)
        io.restassured.RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return objectMapper;
                })
        );
    }

    @Test
    @Order(1)
    void shouldCreatePersonSuccessfully() {
        PersonRequest request = input.mockRequest();
        RequestSpecification reqSpec = buildReqSpecification(request);

        PersonResponse createdPerson = given(reqSpec)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(PersonResponse.class);

        assertThat(createdPerson).isNotNull();
        assertThat(createdPerson.getId()).isNotNull();
        assertThat(createdPerson.getFirstName()).isEqualTo(request.firstName());
        assertThat(createdPerson.getLastName()).isEqualTo(request.lastName());
        assertThat(createdPerson.getAddress()).isEqualTo(request.address());
        assertThat(createdPerson.getGender()).isEqualTo(request.gender());
        assertThat(createdPerson.getEnabled()).isEqualTo(request.enabled());
    }

    @Test
    @Order(2)
    void shouldDenyCreationWhenOriginIsInvalid() {
        PersonRequest request = input.mockRequest();
        RequestSpecification reqSpec = buildReqSpecification(request, TestConfig.ORIGIN_UNALLOWED);

        String content = given(reqSpec)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertThat(content).isEqualTo("Invalid CORS request");
    }

    @Test
    @Order(3)
    void shouldFindPersonById() {
        RequestSpecification reqSpec = buildReqSpecification();

        PersonResponse personResponse = given(reqSpec)
                .pathParam("id", 1)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonResponse.class);

        assertThat(personResponse).isNotNull();
        assertThat(personResponse.getId()).isEqualTo(1L);
    }

    @Test
    @Order(4)
    void shouldDisablePersonSuccessfully() {
        RequestSpecification reqSpec = buildReqSpecification();

        PersonResponse personResponse = given(reqSpec)
                .pathParam("id", 1)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonResponse.class);

        assertThat(personResponse).isNotNull();
        assertThat(personResponse.getId()).isEqualTo(1L);
        assertThat(personResponse.getEnabled()).isFalse();
    }

    @Test
    @Order(5)
    void shouldUpdatePersonSuccessfully() {
        PersonRequest request = input.mockRequest(25);
        RequestSpecification reqSpec = buildReqSpecification(request);

        PersonResponse updatedPerson = given(reqSpec)
                .pathParam("id", 1)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonResponse.class);

        assertThat(updatedPerson).isNotNull();
        assertThat(updatedPerson.getId()).isNotNull();
        assertThat(updatedPerson.getFirstName()).isEqualTo(request.firstName());
        assertThat(updatedPerson.getLastName()).isEqualTo(request.lastName());
        assertThat(updatedPerson.getAddress()).isEqualTo(request.address());
        assertThat(updatedPerson.getGender()).isEqualTo(request.gender());
        assertThat(updatedPerson.getEnabled()).isFalse(); // because was disabled in before test
    }

    @Test
    @Order(6)
    void shouldDeletePersonSuccessfully() {
        RequestSpecification reqSpec = buildReqSpecification();

        given(reqSpec)
                .pathParam("id", 1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(7)
    void shouldFindAllPersons() {
        RequestSpecification reqSpec = buildReqSpecification();

        PersonResponse[] responseArray = given(reqSpec)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonResponse[].class);

        List<PersonResponse> list = List.of(responseArray);

        assertFalse(list.isEmpty()); // Because migrations seed
    }

    private static RequestSpecification buildReqSpecification() {
        return buildReqSpecification(null, TestConfig.ORIGIN_ALLOWED);
    }

    private static RequestSpecification buildReqSpecification(PersonRequest request) {
        return buildReqSpecification(request, TestConfig.ORIGIN_ALLOWED);
    }

    private static RequestSpecification buildReqSpecification(PersonRequest request, String origin) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, origin)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL));

        // REST Assured rejects a null body, so only set it when a request is provided
        if (request != null) builder.setBody(request);

        return builder.build();
    }
}
