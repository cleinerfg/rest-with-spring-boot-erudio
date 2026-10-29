package br.com.erudio.restwithspringbooterudio.exceptions;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ExceptionResponse(Instant timestamp, String message, String details) {
}
