package br.com.erudio.restwithspringbooterudio.modules.book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String author;

    @Column(name = "launch_date", nullable = false)
    private LocalDateTime launchDate;

    @JdbcTypeCode(SqlTypes.DECIMAL)
    @Column(nullable = false, precision = 65, scale = 2)
    private Double price;

    @Column(columnDefinition = "longtext")
    private String title;

    @Builder
    public Book(String author, LocalDateTime launchDate, Double price, String title) {
        this.author = author;
        this.launchDate = launchDate;
        this.price = price;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
