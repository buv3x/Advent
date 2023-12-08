package lt.mem.advent._2023;

import lt.mem.advent.ReaderUtil;
import lt.mem.advent.structure.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2023_3 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/3.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        long total = 0;
        for(int i = 0; i < input.size(); i++) {
            boolean processNumber = false;
            int startIndex = 0;
            StringBuilder numberString = new StringBuilder();
            String line = input.get(i);
            for(int j = 0; j < line.length(); j++) {
                if(Character.isDigit(line.charAt(j))) {
                    if(!processNumber) {
                        processNumber = true;
                        startIndex = j;
                    }
                    numberString.append(line.charAt(j));
                } else {
                    if(processNumber) {
                        processNumber = false;
                        if (checkNumber(startIndex, j - 1, i, input)) {
                            int number = Integer.parseInt(numberString.toString());
                            System.out.println(number);
                            total += number;
                        }
                    }
                    numberString = new StringBuilder();
                }
            }
            if(processNumber) {
                if (checkNumber(startIndex, line.length() - 1, i, input)) {
                    int number = Integer.parseInt(numberString.toString());
                    System.out.println(number);
                    total += number;
                }
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        Map<Point2D, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < input.size(); i++) {
            boolean processNumber = false;
            int startIndex = 0;
            StringBuilder numberString = new StringBuilder();
            String line = input.get(i);
            for(int j = 0; j < line.length(); j++) {
                if(Character.isDigit(line.charAt(j))) {
                    if(!processNumber) {
                        processNumber = true;
                        startIndex = j;
                    }
                    numberString.append(line.charAt(j));
                } else {
                    if(processNumber) {
                        processNumber = false;
                        int number = Integer.parseInt(numberString.toString());
                        checkNumber2(startIndex, j - 1, i, number, input, map);
                    }
                    numberString = new StringBuilder();
                }
            }
            if(processNumber) {
                int number = Integer.parseInt(numberString.toString());
                checkNumber2(startIndex, line.length() - 1, i, number, input, map);
            }
        }
        long total = 0L;
        for (Map.Entry<Point2D, List<Integer>> entry : map.entrySet()) {
            if(entry.getValue().size() == 2) {
                total += (long) entry.getValue().get(0) * entry.getValue().get(1);
            }
        }
        System.out.println(total);
    }

    private static boolean checkNumber(int startIndex, int endIndex, int row, List<String> input) {
        String s = input.get(row);
        if(checkNumberInRow(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s)) {
            return true;
        }

        if(row != 0) {
            s = input.get(row - 1);
            if(checkNumberInRow(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s)) {
                return true;
            }
        }

        if(row != input.size() - 1) {
            s = input.get(row + 1);
            if(checkNumberInRow(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s)) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkNumberInRow(int startIndex, int endIndex, String row) {
        for(int i = startIndex; i <= endIndex; ++i) {
            char c = row.charAt(i);
            if(!Character.isDigit(c) && c != '.') {
                return true;
            }
        }
        return false;
    }

    private static void checkNumber2(int startIndex, int endIndex, int row, int number, List<String> input, Map<Point2D, List<Integer>> map) {
        String s = input.get(row);
        checkNumberInRow2(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s, row, number, map);


        if(row != 0) {
            s = input.get(row - 1);
            checkNumberInRow2(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s, row - 1, number, map);
        }

        if(row != input.size() - 1) {
            s = input.get(row + 1);
            checkNumberInRow2(Math.max(startIndex - 1, 0), Math.min(endIndex + 1, s.length() - 1), s, row + 1, number, map);
        }
    }

    private static void checkNumberInRow2(int startIndex, int endIndex, String row, int rowIndex, int number, Map<Point2D, List<Integer>> map) {
        for(int i = startIndex; i <= endIndex; ++i) {
            char c = row.charAt(i);
            if(c == '*') {
                Point2D point = new Point2D(rowIndex, i);
                if(!map.containsKey(point)) {
                    map.put(point, new ArrayList<>());
                }
                map.get(point).add(number);
            }
        }
    }

}
