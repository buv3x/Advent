package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2017_02 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/2.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        int count = 0;
        for (String row : lineInput) {
            List<Integer> values = Arrays.stream(StringUtils.split(row, ' ')).map(Integer::parseInt).collect(Collectors.toList());
            Integer max = values.stream().max(Integer::compareTo).get();
            Integer min = values.stream().min(Integer::compareTo).get();
            count += max - min;
        }
        System.out.println(count);
    }

    public static void second(List<String> lineInput) {
        int count = 0;
        outer: for (String row : lineInput) {
            List<Integer> values = Arrays.stream(StringUtils.split(row, ' ')).map(Integer::parseInt).collect(Collectors.toList());
            for (Integer value1 : values) {
                for (Integer value2 : values) {
                    if (!value2.equals(value1) && value2 % value1 == 0) {
                        count += value2 / value1;
                        continue outer;
                    }
                }
            }
        }
        System.out.println(count);
    }

}
