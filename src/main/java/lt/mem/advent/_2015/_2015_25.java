package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _2015_25 {

    private static final long a = 20151125;
    private static final long b = 252533;
    private static final long m = 33554393;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/25.txt");
        first(lineInput);
//        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        String line = lineInput.get(0);
        int row = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "at row "), ", "));
        int column = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "column "), "."));

        System.out.println(row + " " + column);

        int diagonals = row + column - 2;
        int order = (diagonals * (diagonals + 1) / 2) + column;

        System.out.println(order);

        long result = a;
        for(int i = 1; i < order; ++i) {
            result = (result * b) % m;
        }

        System.out.println(result);
    }

    public static void second(List<String> lineInput) {
    }


}
