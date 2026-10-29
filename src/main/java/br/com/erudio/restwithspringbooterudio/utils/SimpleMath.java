package br.com.erudio.restwithspringbooterudio.utils;

import org.springframework.stereotype.Component;

@Component
public class SimpleMath {

    public Double sum(Double n1, Double n2) {
        return n1 + n2;
    }

    public Double substraction(Double n1, Double n2) {
        return n1 - n2;
    }

    public Double multiplication(Double n1, Double n2) {
        return n1 * n2;
    }

    public Double divide(Double n1, Double n2) {
        return n1 / n2;
    }

    public Double mean(Double n1, Double n2) {
        return (n1 + n2) / 2;
    }

    public Double sqrt(Double n1) {
        return Math.sqrt(n1);
    }
}
