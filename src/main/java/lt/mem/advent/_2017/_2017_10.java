package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2017_10 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/10.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static int MAX_VALUE = 255;
    private final static List<Integer> tail = Arrays.asList(17, 31, 73, 47, 23);

    public static void first(List<String> lineInput) {
        List<Integer> lengths = Arrays.asList(StringUtils.split(lineInput.get(0), ',')).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> values = new ArrayList<>();
        for(int i = 0; i <= MAX_VALUE; ++i) {
            values.add(i);
        }
        System.out.println(values);

        int position = 0;
        int skip = 0;
        for (Integer length : lengths) {
            List<Integer> newValues = new ArrayList<>();
            int startPosition = position;
            int endPosition = startPosition + length - 1;
            for(int i = startPosition; i <= endPosition; ++i) {
                newValues.add(values.get((endPosition + startPosition - i + (5 * values.size())) % values.size()));
            }
            int index = endPosition + 1;
            while(newValues.size() < values.size()) {
                newValues.add(values.get(index % values.size()));
                index++;
            }
//            System.out.println(newValues);

            values = new ArrayList<>();
            for(int i = 0; i < newValues.size(); ++i) {
                values.add(newValues.get((i - startPosition + (5 * newValues.size())) % newValues.size()));
            }

            System.out.println(values);

            position += (length + skip) % values.size();
            skip++;
        }

        System.out.println(values.get(0) * values.get(1));
    }

    public static void second(List<String> lineInput) {
        List<Integer> values = new ArrayList<>();
        for(int i = 0; i <= MAX_VALUE; ++i) {
            values.add(i);
        }

        List<Integer> lengths = new ArrayList<>();
        String row = lineInput.get(0);
        for (char c : row.toCharArray()) {
            lengths.add((int) c);
        }
        lengths.addAll(tail);

        System.out.println(lengths);

        int position = 0;
        int skip = 0;
        for(int j = 0; j < 64; ++j) {
            for (Integer length : lengths) {
                List<Integer> newValues = new ArrayList<>();
                int startPosition = position;
                int endPosition = startPosition + length - 1;
                for (int i = startPosition; i <= endPosition; ++i) {
                    newValues.add(values.get((endPosition + startPosition - i + (5 * values.size())) % values.size()));
                }
                int index = endPosition + 1;
                while (newValues.size() < values.size()) {
                    newValues.add(values.get(index % values.size()));
                    index++;
                }

                values = new ArrayList<>();
                for (int i = 0; i < newValues.size(); ++i) {
                    values.add(newValues.get((i - startPosition + (5 * newValues.size())) % newValues.size()));
                }

//                System.out.println(values);

                position += length + skip;
                position = position % values.size();
                skip++;
            }
        }

        System.out.println(values);

        List<Integer> denseValues = new ArrayList<>();
        for(int i = 0; i < 16; ++i) {
            int value = 0;
            for(int j = 0; j < 16; ++j) {
                value ^= values.get((i*16) + j);
            }
            denseValues.add(value);
        }

        System.out.println(denseValues);
        StringBuilder sb = new StringBuilder();
        for (Integer denseValue : denseValues) {
            sb.append(StringUtils.leftPad(Integer.toHexString(denseValue), 2, '0'));
        }
        System.out.println(sb);

    }

}
