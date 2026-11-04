package br.com.erudio.restwithspringbooterudio.modules.book.data.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse extends RepresentationModel<BookResponse> {
    private Long id;
    private String author;
    private LocalDateTime launchDate;
    private Double price;
    private String title;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookResponse that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
