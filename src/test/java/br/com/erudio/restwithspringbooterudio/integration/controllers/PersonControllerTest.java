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
import static org.junit.jupiter.api.Assertions.*;

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
    void create() {
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

        assertNotNull(createdPerson.getId());
        assertEquals(createdPerson.getFirstName(), request.firstName());
        assertEquals(createdPerson.getLastName(), request.lastName());
        assertEquals(createdPerson.getAddress(), request.address());
        assertEquals(createdPerson.getGender(), request.gender());
    }

    @Test
    @Order(2)
    void createTestWithWrongOrigin() {
        PersonRequest request = input.mockRequest();
        RequestSpecification reqSpec = buildReqSpecification(
                request, TestConfig.ORIGIN_UNALLOWED
        );

        String content = given(reqSpec)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void getById() {
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

        assertEquals(1L, personResponse.getId());
    }

    @Test
    @Order(4)
    void update() {
        PersonRequest request = input.mockRequest(25);
        RequestSpecification reqSpec = buildReqSpecification(request);

        PersonResponse updatedPerson = given(reqSpec)
                .when()
                .pathParam("id", 1)
                .put("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonResponse.class);

        assertNotNull(updatedPerson.getId());
        assertEquals(updatedPerson.getFirstName(), request.firstName());
        assertEquals(updatedPerson.getLastName(), request.lastName());
        assertEquals(updatedPerson.getAddress(), request.address());
        assertEquals(updatedPerson.getGender(), request.gender());
    }

    @Test
    @Order(5)
    void delete() {
        RequestSpecification reqSpec = buildReqSpecification();

        given(reqSpec)
                .pathParam("id", 1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void getAll() {
        RequestSpecification reqSpec = buildReqSpecification();

        PersonResponse[] responseArray = given(reqSpec)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
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
