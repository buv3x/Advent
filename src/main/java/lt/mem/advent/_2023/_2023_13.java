package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_13 {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/13.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        input.add("");
        List<String> pattern = new ArrayList<>();
        long total = 0;
        for (String line : input) {
            if(StringUtils.isBlank(line)) {
                total += getValue(pattern, -1L);
                pattern = new ArrayList<>();
            } else {
                pattern.add(line);
            }
        }
        System.out.println(total);
    }


    public static void second(List<String> input) {
        input.add("");
        List<String> pattern = new ArrayList<>();
        long total = 0;
        for (String line : input) {
            if(StringUtils.isBlank(line)) {
                long oldValue = getValue(pattern, -1L);
                total += calculateCorruptedValues(pattern, oldValue);
                pattern = new ArrayList<>();
            } else {
                pattern.add(line);
            }
        }
        System.out.println(total);
    }

    private static long calculateCorruptedValues(List<String> pattern, long oldValue) {
        for(int i = 0; i < pattern.size(); ++i) {
            for(int j = 0; j < pattern.get(i).length(); ++j) {
                List<String> corruptedPattern = corruptPattern(pattern, j, i);
                long value = getValue(corruptedPattern, oldValue);
                if(value != -1L && value != oldValue) {
                    System.out.println(value + " (" + j + "," + i + ") != " + oldValue);
                    return value;
                }
            }
        }
        throw new IllegalArgumentException();
    }


    private static List<String> corruptPattern(List<String> pattern, int x, int y) {
        List<StringBuilder> corruptedPattern = new ArrayList<>();
        for (int i = 0; i < pattern.size(); ++i) {
            corruptedPattern.add(new StringBuilder(pattern.get(i)));
        }
        corruptedPattern.get(y).setCharAt(x, corruptedPattern.get(y).charAt(x) == '#' ? '.' : '#');
        return corruptedPattern.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }


    private static long getValue(List<String> pattern, long oldValue) {
        int row = processPattern(pattern, oldValue >= 100 ? oldValue / 100 : -1L);
        if(row != -1) {
            return (row + 1) * 100L;
        } else {
            List<String> reversedPattern = reversePattern(pattern);
            int column = processPattern(reversedPattern, oldValue < 100 ? oldValue: -1L);
            if(column != -1) {
                return column + 1L;
            } else {
                return -1L;
            }
        }
    }

    private static int processPattern(List<String> pattern, long oldValue) {
        for(int i = 0; i < pattern.size() - 1; ++i) {
            int maxIndex = Math.max(0, 2 * i + 2 - pattern.size());
            boolean found = true;
            for(int j = i; j >= maxIndex; --j) {
                if(!pattern.get(j).equals(pattern.get(2 * i + 1 - j))) {
                    found = false;
                    break;
                }
            }
            if(found && i + 1 != oldValue) {
                return i;
            }
        }
        return -1;
    }

    private static List<String> reversePattern(List<String> pattern) {
        List<StringBuilder> reversedPattern = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length(); ++i) {
            reversedPattern.add(new StringBuilder());
        }
        for (String line : pattern) {
            for (int i = 0; i < line.length(); ++i) {
                reversedPattern.get(i).append(line.charAt(i));
            }
        }
        return reversedPattern.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }


}
