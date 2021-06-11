package com.ubs.sodoku.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ResultValidatorTest {

    @Mock
    private CSVParser mockParser;

    @InjectMocks
    private ResultValidator validator;

    @Test
    public void whenNoArgumentsWeGetError() {

        String[] emptyArray = {};

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            validator.validate(emptyArray);
        });

        assertEquals("You must provide one argument, the file name to validate.", expected.getMessage());
    }

    @Test
    public void whenTooManyArgumentsWeGetError() {

        String[] threeArgArray = {"too", "many", "arguments"};

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            validator.validate(threeArgArray);
        });

        assertEquals("You must provide one argument, the file name to validate.", expected.getMessage());
    }

    @Test
    public void whenOneRowInvalidYouGetError(){

        String fileName = "some-file.csv";
        String[] args = {fileName};

        int[][] incorrectSolution={

                {9,3,4,5,6,6,7,2,1},
                {1,2,7,4,9,3,5,8,6},
                {8,6,5,7,1,2,3,9,4},
                {7,5,1,8,4,9,6,3,2},
                {2,9,8,1,3,6,4,7,5},
                {6,4,3,2,5,7,9,1,8},
                {4,1,9,3,2,8,8,6,7},
                {3,8,2,6,7,4,1,5,9},
                {5,7,6,9,8,1,2,4,3}
        };
        Mockito.when(mockParser.toSquares(fileName)).thenReturn(incorrectSolution);
        SodokuException expected = assertThrows(SodokuException.class, () -> {
            validator.validate(args);
        });

        assertEquals("There is an issue with the row provided values : [9, 3, 4, 5, 6, 6, 7, 2, 1]", expected.getMessage());
    }

    @Test
    public void whenOneColumnInvalidYouGetError(){

        String fileName = "some-file.csv";
        String[] args = {fileName};

        int[][] incorrectSolution={

                {9,3,4,5,6,8,7,2,1},
                {1,2,7,4,9,3,5,8,6},
                {8,6,5,7,1,2,3,9,4},
                {7,5,1,8,4,9,6,3,2},
                {2,9,8,1,3,6,4,7,5},
                {6,4,3,2,5,7,4,1,8},
                {4,1,9,3,2,5,8,6,7},
                {4,8,2,6,7,4,1,5,9},
                {5,7,6,9,8,1,2,4,3}
        };
        Mockito.when(mockParser.toSquares(fileName)).thenReturn(incorrectSolution);
        SodokuException expected = assertThrows(SodokuException.class, () -> {
            validator.validate(args);
        });

        assertEquals("There is an issue with the column provided values : [9, 1, 8, 7, 2, 6, 4, 4, 5]", expected.getMessage());
    }

    @Test
    public void whenOneSquareInvalidYouGetError(){

        String fileName = "some-file.csv";
        String[] args = {fileName};

        int[][] incorrectSolution={

                {9,3,4,5,6,8,7,2,1},
                {1,2,7,4,9,3,5,8,6},
                {8,6,2,7,1,2,3,9,4},
                {7,5,1,8,4,9,6,3,2},
                {2,9,8,1,3,6,4,7,5},
                {6,4,3,2,5,7,9,1,8},
                {4,1,9,3,2,5,8,6,7},
                {3,8,2,6,7,4,1,5,9},
                {5,7,6,9,8,1,2,4,3}
        };
        Mockito.when(mockParser.toSquares(fileName)).thenReturn(incorrectSolution);
        SodokuException expected = assertThrows(SodokuException.class, () -> {
            validator.validate(args);
        });

        assertEquals("There is an issue with the sub-square provided values : [9, 3, 4, 1, 2, 7, 8, 6, 2]", expected.getMessage());
    }

    @Test
    public void whenCorrectSolutionNoException(){

        String fileName = "some-file.csv";
        String[] args = {fileName};

        int[][] correctSolution={

                {9,3,4,5,6,8,7,2,1},
                {1,2,7,4,9,3,5,8,6},
                {8,6,5,7,1,2,3,9,4},
                {7,5,1,8,4,9,6,3,2},
                {2,9,8,1,3,6,4,7,5},
                {6,4,3,2,5,7,9,1,8},
                {4,1,9,3,2,5,8,6,7},
                {3,8,2,6,7,4,1,5,9},
                {5,7,6,9,8,1,2,4,3}
        };
        Mockito.when(mockParser.toSquares(fileName)).thenReturn(correctSolution);
        validator.validate(args);
    }



}