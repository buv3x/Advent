package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2015_17 {

    private static final List<Integer> jugs = new ArrayList<>();

    private static int TOTAL = 150;

    private static final Map<Integer, Integer> jugsCount = new HashMap<>();

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/17.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        for (String line : lineInput) {
            jugs.add(Integer.parseInt(line));
        }
        int total = process(0, 0);
        System.out.println(total);
    }

    private static int process(int sum, int index) {
        if(sum > TOTAL) {
             return 0;
        }
        if(sum == TOTAL) {
            return 1;
        }
        if(index == jugs.size()) {
            return 0;
        }
        int jug = jugs.get(index);
        return process(sum, index + 1)  + process(sum + jug, index + 1);
    }

    public static void second(List<String> lineInput) {
        for (String line : lineInput) {
            jugs.add(Integer.parseInt(line));
        }
        process2(0, 0, 0);
        System.out.println(jugsCount);

        System.out.println(jugsCount.get(jugsCount.keySet().stream().min(Integer::compareTo).get()));
    }

    private static void process2(int sum, int count, int index) {
        if(sum > TOTAL) {
            return;
        }
        if(sum == TOTAL) {
            if(!jugsCount.containsKey(count)) {
                jugsCount.put(count, 0);
            }
            jugsCount.put(count, jugsCount.get(count) + 1);
            return;
        }
        if(index == jugs.size()) {
            return;
        }
        int jug = jugs.get(index);
        process2(sum, count, index + 1);
        process2(sum + jug, count + 1, index + 1);
    }


}
