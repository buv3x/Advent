package lt.mem.advent._2024;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;

public class _2024_9 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/9.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        String line = input.get(0);
        List<Integer> sequence = new ArrayList<>(line.length() * 10);
        for(int i = 0; i < line.length(); ++i) {
            int length = Integer.parseInt(line.charAt(i) + "");
            int value = (i % 2 == 0) ? i / 2 : -1;
            for (int j = 0; j < length; ++j) {
                sequence.add(value);
            }
        }

//        System.out.println(sequence);

        int j = sequence.size() - 1;
        for(int i = 0; i < sequence.size(); ++i) {
            if(sequence.get(i).equals(-1)) {
                while(j > i && sequence.get(j).equals(-1)) {
                    j--;
                }
                if(j <= i) {
                    break;
                }
                sequence.set(i, sequence.get(j));
                sequence.set(j, -1);
            }
        }

//        System.out.println(sequence);

        BigInteger total = BigInteger.ZERO;

        int i = 0;
        while(!sequence.get(i).equals(-1)) {
            total = total.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(sequence.get(i))));
            ++i;
        }

        System.out.println(total);

    }


    public static void second(List<String> input) {
        String line = input.get(0);
        List<Integer> sequence = new ArrayList<>(line.length() * 10);
        for(int i = 0; i < line.length(); ++i) {
            int length = Integer.parseInt(line.charAt(i) + "");
            int value = (i % 2 == 0) ? i / 2 : -1;
            for (int j = 0; j < length; ++j) {
                sequence.add(value);
            }
        }

//        System.out.println(sequence);

        int j = sequence.size() - 1;
        while (j > 0) {
            Integer value = sequence.get(j);
            if(value.equals(-1)) {
                j--;
            } else {
                int k = j - 1;
                while(k >= 0 && sequence.get(k).equals(value)) {
                    k--;
                }
                int valueLength = j - k;

                int i = 0;
                while(i <= k) {
                    if(!sequence.get(i).equals(-1)) {
                        i++;
                    } else {
                        int m = i + 1;
                        while(sequence.get(m).equals(-1)) {
                            m++;
                        }
                        int gapLength = m - i;
                        if(gapLength >= valueLength) {
                            for(int n = 0; n < valueLength; ++n) {
                                sequence.set(i + n, value);
                                sequence.set(j - n, -1);
                            }
//                            System.out.println(sequence);
                            break;
                        }
                        i = m;
                    }
                }

//                System.out.println(value + " " + valueLength);
                j = k;
            }
        }

        BigInteger total = BigInteger.ZERO;


        for(int i = 0; i < sequence.size(); ++i) {
            if(!sequence.get(i).equals(-1)) {
                total = total.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(sequence.get(i))));
            }
        }

        System.out.println(total);

    }

}
