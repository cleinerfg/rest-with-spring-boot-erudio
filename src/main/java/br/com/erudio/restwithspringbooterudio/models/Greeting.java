package br.com.erudio.restwithspringbooterudio.models;

import lombok.Builder;

@Builder
public record Greeting(Long id, String content) {
}
