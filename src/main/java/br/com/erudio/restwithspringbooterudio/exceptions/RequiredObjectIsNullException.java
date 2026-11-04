package br.com.erudio.restwithspringbooterudio.exceptions;

public class RequiredObjectIsNullException extends RuntimeException {
    public RequiredObjectIsNullException() {
        super("It is not allowed to persist a null object!");
    }
}
