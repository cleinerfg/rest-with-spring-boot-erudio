package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.modules.person.data.MockPerson;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @Mock
    PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        var person = input.mockEntity(1);

        when(repository.findById(1L))
                .thenReturn(Optional.of(person));

        var result = service.findById(1L);

        assertNotNull(result, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(person.getId(), result.getId(), "ID mismatch");
        assertEquals(person.getFirstName(), result.getFirstName(), "First name mismatch");
        assertEquals(person.getLastName(), result.getLastName(), "Last name mismatch");
        assertEquals(person.getAddress(), result.getAddress(), "Address mismatch");
        assertEquals(person.getGender(), result.getGender(), "Gender mismatch");
    }

    @Test
    void findAll() {
        List<Person> persons = input.mockEntityList();

        when(repository.findAll()).thenReturn(persons);
        List<PersonResponse> result = service.findAll();

        assertNotNull(result, "The response entity must not be null");
        assertEquals(result.size(), persons.size(), "Number of persons mismatch");

        var firstResult = result.getFirst();
        assertNotNull(firstResult, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(firstResult.getId(), persons.getFirst().getId(), "ID mismatch");
        assertEquals(firstResult.getFirstName(), persons.getFirst().getFirstName(), "First name mismatch");
        assertEquals(firstResult.getLastName(), persons.getFirst().getLastName(), "Last name mismatch");
        assertEquals(firstResult.getAddress(), persons.getFirst().getAddress(), "Address mismatch");
        assertEquals(firstResult.getGender(), persons.getFirst().getGender(), "Gender mismatch");
    }

    @Test
    void create() {
        var person = input.mockEntity(1);
        var request = input.mockRequest(1);

        when(repository.save(any(Person.class)))
                .thenReturn(person);

        PersonResponse result = service.create(request);

        assertNotNull(result, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(person.getId(), result.getId(), "ID mismatch");
        assertEquals(person.getFirstName(), result.getFirstName(), "First name mismatch");
        assertEquals(person.getLastName(), result.getLastName(), "Last name mismatch");
        assertEquals(person.getAddress(), result.getAddress(), "Address mismatch");
        assertEquals(person.getGender(), result.getGender(), "Gender mismatch");
    }

    @Test
    void testCreateWithNullRequest() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> service.create(null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void delete() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void update() {
        var person = input.mockEntity(1);
        var request = input.mockRequest(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(any(Person.class))).thenReturn(person);

        PersonResponse result = service.update(1L, request);

        assertNotNull(result, "The response entity must not be null");
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        assertEquals(1, result.getId(), "ID mismatch");
        assertEquals(request.firstName(), result.getFirstName(), "First name mismatch");
        assertEquals(request.lastName(), result.getLastName(), "Last name mismatch");
        assertEquals(request.address(), result.getAddress(), "Address mismatch");
        assertEquals(request.gender(), result.getGender(), "Gender mismatch");
    }

    @Test
    void testUpdateWithNullRequest() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> service.update(1L, null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
