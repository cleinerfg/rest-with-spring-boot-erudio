package br.com.erudio.restwithspringbooterudio.modules.book;

import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.modules.book.data.MockBook;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;
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
class BookServiceTest {

    MockBook input;

    @Mock
    BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        var book = input.mockEntity(1);

        when(repository.findById(1L))
                .thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(book.getId(), result.getId(), "ID mismatch");
        assertEquals(book.getAuthor(), result.getAuthor(), "Author mismatch");
        assertEquals(book.getLaunchDate(), result.getLaunchDate(), "Launch date mismatch");
        assertEquals(book.getPrice(), result.getPrice(), "Price mismatch");
        assertEquals(book.getTitle(), result.getTitle(), "Title mismatch");
    }

    @Test
    void findAll() {
        List<Book> books = input.mockEntityList();

        when(repository.findAll()).thenReturn(books);
        List<BookResponse> result = service.findAll();

        assertNotNull(result, "The response entity must not be null");
        assertEquals(books.size(), result.size(), "Number of books mismatch");

        var firstResult = result.getFirst();
        assertNotNull(firstResult, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = firstResult.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(books.getFirst().getId(), firstResult.getId(), "ID mismatch");
        assertEquals(books.getFirst().getAuthor(), firstResult.getAuthor(), "Author mismatch");
        assertEquals(books.getFirst().getLaunchDate(), firstResult.getLaunchDate(), "Launch date mismatch");
        assertEquals(books.getFirst().getPrice(), firstResult.getPrice(), "Price mismatch");
        assertEquals(books.getFirst().getTitle(), firstResult.getTitle(), "Title mismatch");
    }

    @Test
    void create() {
        var book = input.mockEntity(1);
        var request = input.mockRequest(1);

        when(repository.save(any(Book.class)))
                .thenReturn(book);

        BookResponse result = service.create(request);

        assertNotNull(result, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(book.getId(), result.getId(), "ID mismatch");
        assertEquals(book.getAuthor(), result.getAuthor(), "Author mismatch");
        assertEquals(book.getLaunchDate(), result.getLaunchDate(), "Launch date mismatch");
        assertEquals(book.getPrice(), result.getPrice(), "Price mismatch");
        assertEquals(book.getTitle(), result.getTitle(), "Title mismatch");
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
        var book = input.mockEntity(1);
        var request = input.mockRequest(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(any(Book.class))).thenReturn(book);

        BookResponse result = service.update(1L, request);

        assertNotNull(result, "The response entity must not be null");

        // HATEOAS Links Assertions
        boolean hasSelfLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasSelfLink, "Link 'self' is missing or invalid");

        boolean hasGetAllLinks = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("getAll")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasGetAllLinks, "Link 'getAll' is missing or invalid");

        boolean hasCreateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1"));
        assertTrue(hasCreateLink, "Link 'create' is missing or invalid");

        boolean hasUpdateLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasUpdateLink, "Link 'update' is missing or invalid");

        boolean hasDeleteLink = result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1"));
        assertTrue(hasDeleteLink, "Link 'delete' is missing or invalid");

        // Entity Fields Assertions
        assertEquals(1, result.getId(), "ID mismatch");
        assertEquals(request.author(), result.getAuthor(), "Author mismatch");
        assertEquals(request.launchDate(), result.getLaunchDate(), "Launch date mismatch");
        assertEquals(request.price(), result.getPrice(), "Price mismatch");
        assertEquals(request.title(), result.getTitle(), "Title mismatch");
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
