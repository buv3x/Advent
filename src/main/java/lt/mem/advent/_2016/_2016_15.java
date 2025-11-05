package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_15 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/15.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }
    public static void first(List<String> lineInput) {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        parse(lineInput, pairs);
        System.out.println(pairs);

        process(pairs);
    }

    private static void parse(List<String> lineInput, List<Pair<Integer, Integer>> pairs) {
        int index = 1;
        for (String row : lineInput) {
            int modulus = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " positions"), "has "));
            int offset = (index + Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, "."), "position "))) % modulus;
            pairs.add(Pair.of(modulus, offset));
            ++index;
        }
    }

    private static void process(List<Pair<Integer, Integer>> pairs) {
        int start = 0;
        int jump = 1;
        for (Pair<Integer, Integer> pair : pairs) {
            int newStart = start;
            while((newStart + pair.getRight()) % pair.getLeft() != 0) {
                newStart += jump;
            }
            jump *= pair.getLeft();
            start = newStart;
        }
        System.out.println(start);
    }

    public static void second(List<String> lineInput) {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        parse(lineInput, pairs);
        pairs.add(Pair.of(11, pairs.size() + 1));
        System.out.println(pairs);

        process(pairs);
    }

}
