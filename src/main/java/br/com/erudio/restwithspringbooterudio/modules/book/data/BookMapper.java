package br.com.erudio.restwithspringbooterudio.modules.book.data;

import br.com.erudio.restwithspringbooterudio.modules.book.Book;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookRequest;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookMapper {

    public static BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .launchDate(book.getLaunchDate())
                .price(book.getPrice())
                .title(book.getTitle())
                .build();
    }

    public static Book toEntity(BookRequest request) {
        return Book.builder()
                .author(request.author())
                .launchDate(request.launchDate())
                .price(request.price())
                .title(request.title())
                .build();
    }
}
