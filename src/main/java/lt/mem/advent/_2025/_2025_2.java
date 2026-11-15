package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class _2025_2 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/2.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        String[] splitted = StringUtils.split(input.get(0), ',');
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String s : splitted) {
            pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(s, "-")), Long.valueOf(ReaderUtil.stringAfter(s, "-"))));
        }
        System.out.println(pairs);
        List<Pair<Long, Long>> truePairs = new ArrayList<>();
        for (Pair<Long, Long> pair : pairs) {
            int leftLength = pair.getLeft().toString().length();
            int rightLength = pair.getRight().toString().length();
            for(int i = leftLength; i <= rightLength; ++i) {
                if(i % 2 != 0) {
                    continue;
                }
                long left;
                if(i == leftLength) {
                    left = pair.getLeft();
                } else {
                    left = (long) Math.pow(10, i - 1);
                }
                long right;
                if(i == rightLength) {
                    right = pair.getRight();
                } else {
                    right = ((long) Math.pow(10, i)) - 1;
                }
                truePairs.add(Pair.of(left, right));
            }
        }
        System.out.println(truePairs);
        long total = 0;
        for (Pair<Long, Long> truePair : truePairs) {
            int length = truePair.getLeft().toString().length();
            long left = Long.parseLong(truePair.getLeft().toString().substring(0, length / 2));
            long right = Long.parseLong(truePair.getRight().toString().substring(0, length / 2));
            for(long i = left; i <= right; ++i) {
                long doubleValue = Long.parseLong(i + "" + i);
                if(doubleValue >= truePair.getLeft() && doubleValue <= truePair.getRight()) {
                    total += doubleValue;
                }
            }
        }
        System.out.println(total);

    }

    public static void second(List<String> input) {
        String[] splitted = StringUtils.split(input.get(0), ',');
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String s : splitted) {
            pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(s, "-")), Long.valueOf(ReaderUtil.stringAfter(s, "-"))));
        }
        System.out.println(pairs);
        long total = 0;
        for (Pair<Long, Long> pair : pairs) {
            for(long i = pair.getLeft(); i <= pair.getRight(); ++i) {
                String value = i + "";
                int length = value.length();
                inner: for(int j = 1; j <= length / 2; ++j) {
                    if(length % j != 0) {
                        continue;
                    }
                    String substring = value.substring(0, j);
                    for(int k = 0; k < length / j; ++k) {
                        String checkSubstring = value.substring(k * j, (k * j) + j);
                        if(!checkSubstring.equals(substring)) {
                            continue inner;
                        }
                    }
                    System.out.println(i);
                    total += i;
                    break;
                }
            }
        }
        System.out.println(total);
    }

}
