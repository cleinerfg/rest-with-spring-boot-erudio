package br.com.erudio.restwithspringbooterudio.modules.book.data;

import br.com.erudio.restwithspringbooterudio.modules.book.Book;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookRequest;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private MockBook input;

    @BeforeEach
    void setUp() {
        input = new MockBook();
    }

    @Test
    @DisplayName("Should successfully map a Book entity to BookResponse")
    void shouldMapBookToBookResponseSuccessfully() {
        // Arrange
        Book mock = input.mockEntity(1);

        // Act
        BookResponse response = BookMapper.toResponse(mock);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mock.getId());
        assertThat(response.getAuthor()).isEqualTo(mock.getAuthor());
        assertThat(response.getLaunchDate()).isEqualTo(mock.getLaunchDate());
        assertThat(response.getPrice()).isEqualTo(mock.getPrice());
        assertThat(response.getTitle()).isEqualTo(mock.getTitle());
    }

    @Test
    @DisplayName("Should successfully map a BookRequest to a Book entity")
    void shouldMapBookRequestToBookEntitySuccessfully() {
        // Arrange
        BookRequest request = input.mockRequest(1);

        // Act
        Book book = BookMapper.toEntity(request);

        // Assert
        assertThat(book).isNotNull();
        assertThat(book.getId()).isNull();
        assertThat(book.getAuthor()).isEqualTo(request.author());
        assertThat(book.getLaunchDate()).isEqualTo(request.launchDate());
        assertThat(book.getPrice()).isEqualTo(request.price());
        assertThat(book.getTitle()).isEqualTo(request.title());
    }
}
