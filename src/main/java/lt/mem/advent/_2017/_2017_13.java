package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class _2017_13 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/13.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }


    public static void first(List<String> lineInput) {
        int total = 0;
        for (String row : lineInput) {
            int index = Integer.parseInt(ReaderUtil.stringBefore(row, ": "));
            int depth = Integer.parseInt(ReaderUtil.stringAfter(row, ": "));

            if(index % ((depth - 1) * 2) == 0) {
                total += index * depth;
            }

        }
        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        List<Pair<Integer, Integer>> columns = new ArrayList<>();
        for (String row : lineInput) {
            int index = Integer.parseInt(ReaderUtil.stringBefore(row, ": "));
            int depth = Integer.parseInt(ReaderUtil.stringAfter(row, ": "));
            columns.add(Pair.of((depth - 1) * 2, index));
        }
        System.out.println(columns);
        process(columns);
    }

    private static void process(List<Pair<Integer, Integer>> pairs) {
        int start = -1;
        outer: while(true) {
            start++;
            for (Pair<Integer, Integer> pair : pairs) {
                if((start + pair.getRight()) % pair.getLeft() == 0) {
                    continue outer;
                }
            }
            System.out.println(start);
            return;
        }
    }

}
