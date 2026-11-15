package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;

public class _2025_1 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/1.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int START = 50;

    public static void first(List<String> input) {
        int total = 0;
        int dial = START;
        for (String line : input) {
            int value = Integer.parseInt(line.substring(1));
            if(line.charAt(0) == 'L') {
                dial = (100 + dial - value) % 100;
            } else {
                dial = (dial + value) % 100;
            }
            if(dial == 0) {
                total++;
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        int total = 0;
        int dial = START;
        for (String line : input) {
            int value = Integer.parseInt(line.substring(1));
            if(line.charAt(0) == 'L') {
                for(int i = 0; i < value; ++i) {
                    dial--;
                    dial = (100 + dial) % 100;
                    if(dial == 0) {
                        total++;
                    }
                }
            } else {
                for(int i = 0; i < value; ++i) {
                    dial++;
                    dial = (100 + dial) % 100;
                    if(dial == 0) {
                        total++;
                    }
                }
            }
        }
        System.out.println("Result: " + total);
    }

}
