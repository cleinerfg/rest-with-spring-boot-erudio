package br.com.erudio.restwithspringbooterudio.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    // https://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{n1}/{n2}")
    public Double sum(
            @PathVariable("n1") String n1,
            @PathVariable("n2") String n2
    ) {
        return convertToDouble(n1) + convertToDouble(n2);
    }

    private Double convertToDouble(String strNum) {
        if (strNum == null || strNum.isEmpty()) throw new IllegalArgumentException("Is not a number");
        String number = strNum.replace(",", ".");
        return Double.parseDouble(number);
    }
}
