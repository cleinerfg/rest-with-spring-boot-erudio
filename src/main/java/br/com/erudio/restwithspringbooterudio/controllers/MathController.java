package br.com.erudio.restwithspringbooterudio.controllers;

import br.com.erudio.restwithspringbooterudio.utils.NumberConverter;
import br.com.erudio.restwithspringbooterudio.utils.SimpleMath;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
@RequiredArgsConstructor
public class MathController {

    private final SimpleMath simpleMath;

    // https://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{n1}/{n2}")
    public Double sum(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return simpleMath.sum(
                NumberConverter.convertToDouble(n1),
                NumberConverter.convertToDouble(n2)
        );
    }

    // https://localhost:8080/math/subtraction/3/5
    @RequestMapping("/subtraction/{n1}/{n2}")
    public Double subtraction(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return simpleMath.substraction(
                NumberConverter.convertToDouble(n1),
                NumberConverter.convertToDouble(n2)
        );
    }

    // https://localhost:8080/math/multiplication/3/5
    @RequestMapping("/multiplication/{n1}/{n2}")
    public Double multiplication(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return simpleMath.multiplication(
                NumberConverter.convertToDouble(n1),
                NumberConverter.convertToDouble(n2)
        );
    }

    // https://localhost:8080/math/division/3/5
    @RequestMapping("/division/3/5")
    public Double division(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return simpleMath.divide(
                NumberConverter.convertToDouble(n1),
                NumberConverter.convertToDouble(n2)
        );
    }

    // https://localhost:8080/math/mean/3/5
    @RequestMapping("/mean/{n1}/{n2}")
    public Double mean(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return simpleMath.mean(
                NumberConverter.convertToDouble(n1),
                NumberConverter.convertToDouble(n2)
        );
    }

    // https://localhost:8080/math/square-root/3
    @RequestMapping("/square-root/{n}")
    public Double squareRoot(
            @PathVariable("n") String n
    ) {
        return simpleMath.sqrt(
                NumberConverter.convertToDouble(n)
        );
    }
}
