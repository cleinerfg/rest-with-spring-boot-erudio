package br.com.erudio.restwithspringbooterudio.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfig {

    public static final int SERVER_PORT = 8888;
    public static final String HEADER_PARAM_AUTHORIZATION = "Authorization";
    public static final String HEADER_PARAM_ORIGIN = "Origin";

    public static final String ORIGIN_ALLOWED = "http://localhost:8081";
    public static final String ORIGIN_UNALLOWED = "http://other.domain:8082";
}
