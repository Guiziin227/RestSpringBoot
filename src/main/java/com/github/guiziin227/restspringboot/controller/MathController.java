package com.github.guiziin227.restspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new Exception();
        return convertToDouble(numberOne ) + convertToDouble(numberTwo);
    }

    private boolean isNumeric(String number) {
        if (number == null || number.isEmpty()) return false;
        String n = number.replace(",", ".");
        return (n.matches("[-+]?[0-9]*\\.?[0-9]+"));
    }

    private Double convertToDouble(String number) {
        if (number == null || number.isEmpty()) throw new ArithmeticException();
        String n = number.replace(",", ".");
        return Double.parseDouble(n);
    }
}
