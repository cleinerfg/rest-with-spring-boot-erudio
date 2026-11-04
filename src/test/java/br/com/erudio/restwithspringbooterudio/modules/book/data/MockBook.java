package br.com.erudio.restwithspringbooterudio.modules.book.data;

import br.com.erudio.restwithspringbooterudio.modules.book.Book;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookRequest;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    private static final LocalDateTime BASE_DATE = LocalDateTime.of(2024, 1, 1, 10, 0);

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookRequest mockRequest() {
        return mockRequest(0);
    }

    public Book mockEntity(Integer number) {
        var book = Book.builder()
                .author("Author" + number)
                .launchDate(BASE_DATE.plusDays(number))
                .price(10.0 + number)
                .title("Title" + number)
                .build();

        try {
            var idField = Book.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(book, number.longValue());
            return book;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public BookRequest mockRequest(Integer number) {
        return BookRequest.builder()
                .author("Author" + number)
                .launchDate(BASE_DATE.plusDays(number))
                .price(10.0 + number)
                .title("Title" + number)
                .build();
    }

    public BookResponse mockResponse(Integer number) {
        return BookResponse.builder()
                .id(number.longValue())
                .author("Author" + number)
                .launchDate(BASE_DATE.plusDays(number))
                .price(10.0 + number)
                .title("Title" + number)
                .build();
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }
}
