package br.com.erudio.restwithspringbooterudio.modules.book.data.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookRequest(
        String author,
        LocalDateTime launchDate,
        Double price,
        String title
) {
}
