package br.com.erudio.restwithspringbooterudio.modules.person.data.dto;

import lombok.Builder;

@Builder
public record PersonResponse(
        Long id,
        String firstName,
        String lastName,
        String address,
        String gender
) {
}
