package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class _2025_3 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/3.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        int total = 0;
        for (String row : input) {
            int maxFirst = 0;
            int maxFirstIndex = 0;
            for(int i = 0; i <= row.length() - 2; i++) {
                int value = Integer.parseInt(row.charAt(i) + "");
                if(value > maxFirst) {
                    maxFirst = value;
                    maxFirstIndex = i;
                }
            }
            int maxSecond = 0;
            for(int i = maxFirstIndex + 1; i <= row.length() - 1; i++) {
                int value = Integer.parseInt(row.charAt(i) + "");
                if(value > maxSecond) {
                    maxSecond = value;
                }
            }
            total += (maxFirst * 10) + maxSecond;
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        long total = 0;
        for (String row : input) {
            List<Integer> digits = new ArrayList<>();
            int index = -1;
            for(int k = 11; k >= 0; --k) {
                int max = 0;
                for(int i = index + 1; i < row.length() - k; i++) {
                    int value = Integer.parseInt(row.charAt(i) + "");
                    if(value > max) {
                        max = value;
                        index = i;
                    }
                }
                digits.add(max);
            }
            long joltage = 0;
            for(int k = 11; k >= 0; --k) {
                joltage += ((long) Math.pow(10, k)) * digits.get(11 - k);
            }
//            System.out.println(joltage);
            total += joltage;
        }
        System.out.println(total);
    }

}
