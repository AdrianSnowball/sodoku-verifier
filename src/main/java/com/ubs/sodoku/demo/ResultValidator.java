package com.ubs.sodoku.demo;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ResultValidator {

    private final CSVParser csvParser;

    public ResultValidator(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

    public void validate(String[] args) throws SodokuException {
        if (args.length != 1) {
            throw new SodokuException("You must provide one argument, the file name to validate.");
        }

        int[][] solution = csvParser.toSquares(args[0]);
        extractArraysAndValidate(solution);
    }

    private void extractArraysAndValidate(int[][] solution) {
        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {

            int[] row = solution[rowIndex].clone();
            int[] column = new int[9];
            int[] subSquare = new int[9];

            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                column[columnIndex] = solution[columnIndex][rowIndex];
                subSquare[columnIndex] = fetchNextNumberForSubSquare(solution, rowIndex, columnIndex);
            }
            validate(row, "row");
            validate(column, "column");
            validate(subSquare, "sub-square");
        }
    }

    private int fetchNextNumberForSubSquare(int[][] solution, int rowIndex, int columnIndex) {
        int rowPosition = (rowIndex / 3) * 3 + columnIndex / 3;
        int columnPosition = (rowIndex * 3) % 9 + columnIndex % 3;
        return solution[rowPosition][columnPosition];
    }

    private void validate(int[] rowColumnOrSquare, String issue) {
        Set<Integer> providedNumbers = Arrays.stream(rowColumnOrSquare).boxed().collect(Collectors.toSet());
        Set<Integer> correctNumbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        if (providedNumbers.size() != 9 || !providedNumbers.containsAll(correctNumbers)) {
            throw new SodokuException(
                    String.format("There is an issue with the %s provided values : %s",
                            issue,
                            Arrays.toString(rowColumnOrSquare)));
        }
    }

}
