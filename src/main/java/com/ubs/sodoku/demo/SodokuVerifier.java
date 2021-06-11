package com.ubs.sodoku.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SodokuVerifier implements CommandLineRunner {

    private final ResultValidator resultValidator;

    public SodokuVerifier(ResultValidator resultValidator) {
        this.resultValidator = resultValidator;
    }

    public static void main(String[] args) {
        SpringApplication.run(SodokuVerifier.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            resultValidator.validate(args);
        } catch (SodokuException sodokuException) {
            System.out.println(String.format("INVALID %s", sodokuException.getMessage()));
            return;
        }
        System.out.println("VALID");
    }
}
