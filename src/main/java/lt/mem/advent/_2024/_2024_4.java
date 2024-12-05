package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class _2024_4 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/4.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<List<Character>> grid = new ArrayList<>();
        for (String s : input) {
            List<Character> row = new ArrayList<>();
            for (char c : s.toCharArray()) {
                row.add(c);
            }
            grid.add(row);
        }

        int total = 0;
        for(int i = 0; i < grid.size(); ++i) {
            for(int j = 0; j < grid.get(i).size(); ++j) {
                Character c = grid.get(i).get(j);
                if(c.equals('X')) {
                    if(i > 2) {
                        if(j > 2) {
                            if(grid.get(i - 1).get(j - 1).equals('M') &&
                                    grid.get(i - 2).get(j - 2).equals('A') &&
                                    grid.get(i - 3).get(j - 3).equals('S')) {
                                total++;
                            }
                        }
                        if(grid.get(i - 1).get(j).equals('M') &&
                                grid.get(i - 2).get(j).equals('A') &&
                                grid.get(i - 3).get(j).equals('S')) {
                            total++;
                        }
                        if(j < grid.get(i).size() - 3) {
                            if(grid.get(i - 1).get(j + 1).equals('M') &&
                                    grid.get(i - 2).get(j + 2).equals('A') &&
                                    grid.get(i - 3).get(j + 3).equals('S')) {
                                total++;
                            }
                        }
                    }
                    if(i < grid.size() -3) {
                        if(j > 2) {
                            if(grid.get(i + 1).get(j - 1).equals('M') &&
                                    grid.get(i + 2).get(j - 2).equals('A') &&
                                    grid.get(i + 3).get(j - 3).equals('S')) {
                                total++;
                            }
                        }
                        if(grid.get(i + 1).get(j).equals('M') &&
                                grid.get(i + 2).get(j).equals('A') &&
                                grid.get(i + 3).get(j).equals('S')) {
                            total++;
                        }
                        if(j < grid.get(i).size() - 3) {
                            if(grid.get(i + 1).get(j + 1).equals('M') &&
                                    grid.get(i + 2).get(j + 2).equals('A') &&
                                    grid.get(i + 3).get(j + 3).equals('S')) {
                                total++;
                            }
                        }
                    }

                    if(j > 2) {
                        if(grid.get(i).get(j - 1).equals('M') &&
                                grid.get(i).get(j - 2).equals('A') &&
                                grid.get(i).get(j - 3).equals('S')) {
                            total++;
                        }
                    }
                    if(j < grid.get(i).size() - 3) {
                        if(grid.get(i).get(j + 1).equals('M') &&
                                grid.get(i).get(j + 2).equals('A') &&
                                grid.get(i).get(j + 3).equals('S')) {
                            total++;
                        }
                    }
                }
            }
        }

        System.out.println(total);

    }

    public static void second(List<String> input) {
        List<List<Character>> grid = new ArrayList<>();
        for (String s : input) {
            List<Character> row = new ArrayList<>();
            for (char c : s.toCharArray()) {
                row.add(c);
            }
            grid.add(row);
        }

        int total = 0;
        for(int i = 0; i < grid.size(); ++i) {
            for(int j = 0; j < grid.get(i).size(); ++j) {
                Character c = grid.get(i).get(j);
                if(c.equals('A')) {
                    if(i > 0 && i < grid.size() - 1 && j > 0 && j < grid.get(i).size() - 1) {
                        if((grid.get(i - 1).get(j - 1).equals('M') && grid.get(i + 1).get(j + 1).equals('S')) ||
                                (grid.get(i - 1).get(j - 1).equals('S') && grid.get(i + 1).get(j + 1).equals('M'))) {
                            if((grid.get(i - 1).get(j + 1).equals('M') && grid.get(i + 1).get(j - 1).equals('S')) ||
                                    (grid.get(i - 1).get(j + 1).equals('S') && grid.get(i + 1).get(j - 1).equals('M'))) {
                                total++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(total);
    }


}
