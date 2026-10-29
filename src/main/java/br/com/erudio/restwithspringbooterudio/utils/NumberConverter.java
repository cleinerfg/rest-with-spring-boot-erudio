package br.com.erudio.restwithspringbooterudio.utils;

import br.com.erudio.restwithspringbooterudio.exceptions.UnsupportedMathOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberConverter {

    public static Double convertToDouble(String strNum) {
        if (strNum == null || strNum.isEmpty())
            throw new UnsupportedMathOperation("Please set a numeric value");

        String number = strNum.replace(",", ".");

        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new UnsupportedMathOperation("Please set a numeric value");
        }
    }
}
