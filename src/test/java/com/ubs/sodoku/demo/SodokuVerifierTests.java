package com.ubs.sodoku.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class SodokuVerifierTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Mock
    private ResultValidator mockValidator;

    @InjectMocks
    private SodokuVerifier sodokuVerifier;

    @Test
    public void whenValidatorThrowsErrorWePrintItOut() {

        String[] emptyArray = {};

        doThrow(new SodokuException("Some error")).
                when(mockValidator).validate(emptyArray);

        sodokuVerifier.run(emptyArray);

        assertEquals("INVALID Some error\n".trim(), outContent.toString().trim());

    }

    @Test
    public void whenCorrectSolutionWeGetVALID() {

        String[] emptyArray = {};

        sodokuVerifier.run(emptyArray);

        assertEquals("VALID\n".trim(), outContent.toString().trim());

    }
}
