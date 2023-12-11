package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class _2023_4 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/4.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        long total = 0L;
        for (String line : input) {
            String predLine = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
            String resultLine = line.substring(line.indexOf("|") + 2);

            Set<Integer> results = Arrays.stream(StringUtils.split(resultLine, " "))
                    .map(Integer::valueOf).collect(Collectors.toSet());
            int count = (int) Arrays.stream(StringUtils.split(predLine, " "))
                    .map(Integer::valueOf).filter(p -> results.contains(p)).count();
            System.out.println(count);
            total += (long )Math.pow(2, count - 1);
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<Integer> cardsCount = new ArrayList<>();
        for(int i = 0; i < input.size(); ++i) {
            cardsCount.add(1);
        }

        for(int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            String predLine = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
            String resultLine = line.substring(line.indexOf("|") + 2);

            Set<Integer> results = Arrays.stream(StringUtils.split(resultLine, " "))
                    .map(Integer::valueOf).collect(Collectors.toSet());
            int count = (int) Arrays.stream(StringUtils.split(predLine, " "))
                    .map(Integer::valueOf).filter(results::contains).count();
            Integer currentCount = cardsCount.get(i);
            for(int j = i + 1; j < i + 1 + count; ++j) {
                cardsCount.set(j, cardsCount.get(j) + currentCount);
            }


        }

        long total = 0;
        for (Integer count : cardsCount) {
            System.out.println(count);
            total += count;
        }
        System.out.println(total);
    }

}
