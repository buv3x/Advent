package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class _2016_20 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/20.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        List<Pair<Long, Long>> ranges = parse(lineInput);
//        System.out.println(ranges);

        long minAllowed = 0L;
        for (Pair<Long, Long> range : ranges) {
            if(range.getLeft() > minAllowed) {
                break;
            }
            if(range.getRight() >= minAllowed) {
                minAllowed = range.getRight() + 1;
            }

        }

        System.out.println(minAllowed);
    }

    private static List<Pair<Long, Long>> parse(List<String> lineInput) {
        List<Pair<Long, Long>> ranges = new ArrayList<>();
        for (String row : lineInput) {
            long left = Long.parseLong(ReaderUtil.stringBefore(row, "-"));
            long right = Long.parseLong(ReaderUtil.stringAfter(row, "-"));
            ranges.add(Pair.of(left, right));
        }
        ranges.sort(Comparator.comparing(Pair::getLeft));
        return ranges;
    }

//    private static final long MAX_VALUE = 9L;
    private static final long MAX_VALUE = 4294967295L;

    public static void second(List<String> lineInput) {
        List<Pair<Long, Long>> ranges = parse(lineInput);
        List<Pair<Long, Long>> allowances = new ArrayList<>();
        allowances.add(Pair.of(0L, MAX_VALUE));

        for (Pair<Long, Long> range : ranges) {
            List<Pair<Long, Long>> newAllowances = new ArrayList<>();
            for (Pair<Long, Long> allowance : allowances) {
                if(range.getRight() < allowance.getLeft() || range.getLeft() > allowance.getRight()) {
                    newAllowances.add(allowance);
                } else {
                    if (range.getLeft() <= allowance.getLeft()) {
                        if (range.getRight() < allowance.getRight()) {
                            newAllowances.add(Pair.of(range.getRight() + 1, allowance.getRight()));
                        }
                    } else {
                        newAllowances.add(Pair.of(allowance.getLeft(), range.getLeft() - 1));
                        if (range.getRight() < allowance.getRight()) {
                            newAllowances.add(Pair.of(range.getRight() + 1, allowance.getRight()));
                        }
                    }
                }
            }
            allowances = newAllowances;
//            System.out.println(allowances);
        }
        System.out.println(allowances);

        long total = 0;
        for (Pair<Long, Long> allowance : allowances) {
            total += allowance.getRight() - allowance.getLeft() + 1;
        }
        System.out.println(total);
    }

}
