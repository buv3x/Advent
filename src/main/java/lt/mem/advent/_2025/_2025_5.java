package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2025_5 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/5.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        boolean intervals = true;
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        Set<Long> products = new HashSet<>();
        for (String row : input) {
            if(StringUtils.isBlank(row)) {
                intervals = false;
                continue;
            }

            if(intervals) {
                pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(row, "-")),
                        Long.valueOf(ReaderUtil.stringAfter(row, "-"))));
            } else {
                products.add(Long.valueOf(row));
            }
        }

        System.out.println(pairs);
        System.out.println(products);

        int total = 0;
        outer: for (Long product : products) {
            for (Pair<Long, Long> pair : pairs) {
                if(pair.getLeft() <= product && pair.getRight() >= product) {
                    total++;
                    continue outer;
                }
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String row : input) {
            if(StringUtils.isBlank(row)) {
                break;
            }
            pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(row, "-")),
                    Long.valueOf(ReaderUtil.stringAfter(row, "-"))));
        }

        pairs.sort(Comparator.comparing(Pair::getLeft));
        System.out.println(pairs);

        boolean hadChanges = true;
        outer: while(hadChanges) {
            hadChanges = false;
            List<Pair<Long, Long>> newInputPairs = new ArrayList<>();
            for(int i = 0; i < pairs.size() - 1; ++i) {
                if(pairs.get(i).getRight() < pairs.get(i + 1).getLeft()) {
                    newInputPairs.add(pairs.get(i));
                } else {
                    newInputPairs.add(Pair.of(pairs.get(i).getLeft(), Math.max(pairs.get(i).getRight(), pairs.get(i+1).getRight())));
                    for(int j = i + 2; j < pairs.size(); ++j) {
                        newInputPairs.add(pairs.get(j));
                    }

                    pairs = newInputPairs;
                    hadChanges = true;
                    continue outer;
                }
            }
            newInputPairs.add(pairs.get(pairs.size() - 1));
            pairs = newInputPairs;
        }
        System.out.println(pairs);

        long total = 0;
        for (Pair<Long, Long> pair : pairs) {
            total += (pair.getRight() - pair.getLeft() + 1);
        }
        System.out.println(total);
    }

}
