package com.ubs.sodoku.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {


    CSVParser parser;

    @BeforeEach
    public void setUp(){
        parser = new CSVParser();
    }

    @Test
    public void whenFileNotFoundSodokuException() {

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            parser.toSquares("file.csv");
        });

        assertEquals("File not found 'file.csv'.", expected.getMessage());
    }

    @Test
    public void whenEmptyFileFoundSodokuException() {

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            parser.toSquares("src/test/resources/fixtures/empty.csv");
        });

        assertEquals("Invalid file contents, CSV must have 9 rows.", expected.getMessage());
    }

    @Test
    public void whenBadFormatSodokuException() {

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            parser.toSquares("src/test/resources/fixtures/bad-format.csv");
        });

        assertEquals("Invalid file contents, CSV must only contain integers separated by commas.", expected.getMessage());
    }

    @Test
    public void whenTooManyColumnsSodokuException() {

        SodokuException expected = assertThrows(SodokuException.class, () -> {
            parser.toSquares("src/test/resources/fixtures/too-many-columns.csv");
        });

        assertEquals("Invalid file contents, CSV must have 9 columns on each row.", expected.getMessage());
    }

    @Test
    public void weGetCorrectlyFormattedCSVAsGrid(){

        int[][] expected={

                {9,3,4,5,6,8,7,2,1},
                {4,3,4,5,6,8,7,2,1},
                {5,3,4,5,6,8,7,2,1},
                {6,3,4,5,6,8,7,2,1},
                {7,3,4,5,6,8,7,2,1},
                {8,3,4,5,6,8,7,2,1},
                {4,1,2,9,3,5,8,6,7},
                {3,8,9,6,2,4,1,5,9},
                {5,7,6,7,8,1,2,4,7}
        };

        int[][] squares = parser.toSquares("src/test/resources/fixtures/correct-format-incorrect-solution.csv");

        assertEquals(Arrays.toString(expected[0]), Arrays.toString(squares[0]));
        assertEquals(Arrays.toString(expected[1]), Arrays.toString(squares[1]));
        assertEquals(Arrays.toString(expected[2]), Arrays.toString(squares[2]));
        assertEquals(Arrays.toString(expected[3]), Arrays.toString(squares[3]));
        assertEquals(Arrays.toString(expected[4]), Arrays.toString(squares[4]));
        assertEquals(Arrays.toString(expected[5]), Arrays.toString(squares[5]));
        assertEquals(Arrays.toString(expected[6]), Arrays.toString(squares[6]));
        assertEquals(Arrays.toString(expected[7]), Arrays.toString(squares[7]));
        assertEquals(Arrays.toString(expected[8]), Arrays.toString(squares[8]));
    }

}