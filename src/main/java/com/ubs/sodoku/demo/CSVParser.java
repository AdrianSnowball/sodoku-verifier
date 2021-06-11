package com.ubs.sodoku.demo;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CSVParser {

    public int[][] toSquares(String filename) {

        int[][] allSquares = new int[9][9];

        Path path = Paths.get(filename);

        try {
            List<String> rows = Files.readAllLines(path);

            if (rows.size() != 9) {
                throw new SodokuException("Invalid file contents, CSV must have 9 rows.");
            }
            for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
                allSquares[rowNumber] = rowToIntArray(rows.get(rowNumber));
            }
        } catch (IOException e) {
            throw new SodokuException(String.format("File not found '%s'.", filename));
        }
        return allSquares;
    }

    private int[] rowToIntArray(String csvRow) {

        int[] squares = new int[9];

        String[] columnValues = csvRow.split(",");
        if(columnValues.length != 9){
            throw new SodokuException("Invalid file contents, CSV must have 9 columns on each row.");
        }
        for (int column = 0; column < columnValues.length; column++) {
            squares[column] = toInteger(columnValues[column]);
        }
        return squares;
    }

    private int toInteger(String value) {
        int squareValue;
        try {
            squareValue = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            throw new SodokuException("Invalid file contents, CSV must only contain integers separated by commas.");
        }
        return squareValue;

    }
}
