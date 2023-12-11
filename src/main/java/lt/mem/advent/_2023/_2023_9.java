package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_9 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/9.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        process(input, false);
    }


    public static void second(List<String> input) {
        process(input, true);
    }

    private static void process(List<String> input, boolean reverse) {
        long total = 0;
        for (String line : input) {
            List<Long> values = Arrays.stream(StringUtils.split(line, " ")).map(Long::valueOf).collect(Collectors.toList());
            if(reverse) {
                Collections.reverse(values);
            }
            List<List<Long>> triangle = new ArrayList<>();
            triangle.add(values);
            for(int i = 0; i < values.size(); ++i) {
                List<Long> topValues = triangle.get(triangle.size() - 1);
                List<Long> bottomValues = new ArrayList<>();
                for(int j = 0; j < topValues.size() - 1; ++j) {
                    bottomValues.add(topValues.get(j + 1) - topValues.get(j));
                }
                triangle.add(bottomValues);
                if(allZeroes(bottomValues)) {
                    break;
                }
            }
//            System.out.println(triangle);
            for(int i = triangle.size() - 2; i >= 0; --i) {
                triangle.get(i).add(getLast(triangle.get(i)) + getLast(triangle.get(i + 1)));
            }
//            System.out.println(triangle);
            total += getLast(triangle.get(0));
        }

        System.out.println(total);
    }


    private static Long getLast(List<Long> values) {
        return values.get(values.size() - 1);
    }

    private static boolean allZeroes(List<Long> values) {
        for (Long value : values) {
            if(!value.equals(0L)) {
                return false;
            }
        }
        return true;
    }

}
