package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _2024_3 {

    public static final String MUL = "mul(";
    public static final String DO = "do()";
    public static final String DONT = "don't()";

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/3.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        StringBuilder sb = new StringBuilder();
        for (String row : input) {
            sb.append(row);
        }
        String line = sb.toString();
        boolean removeFirst = !line.startsWith(MUL);


        List<String> strings = new ArrayList<>(Arrays.asList(StringUtils.splitByWholeSeparator(line, MUL)));
        if(removeFirst) {
            strings.remove(0);
        }

        long total = 0;
        for (String string : strings) {
            if(string.contains(")")) {
                String pair = ReaderUtil.stringBefore(string, ")");
                if(pair.contains(",")) {
                    String first = ReaderUtil.stringBefore(pair, ",");
                    String second = ReaderUtil.stringAfter(pair, ",");

                    if(isValid(first) && isValid(second)) {
                        total += Integer.parseInt(first) * Integer.parseInt(second);
                    }
                }
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        StringBuilder sb = new StringBuilder();
        for (String row : input) {
            sb.append(row);
        }

        String line = sb.toString();

        long total = 0;
        boolean doIt = true;
        int nextMul = line.indexOf(MUL);
        int nextDo = line.indexOf(DO);
        int nextDont = line.indexOf(DONT);
        while (nextMul != -1) {
            if(nextDo != -1 && (nextDo < nextMul) && (nextDo < nextDont || nextDont == -1)) {
                doIt = true;
                line = ReaderUtil.stringAfter(line, DO);
            } else if (nextDont != -1 && (nextDont < nextMul) && (nextDont < nextDo || nextDo == -1)) {
                doIt = false;
                line = ReaderUtil.stringAfter(line, DONT);
            } else {
                line = ReaderUtil.stringAfter(line, MUL);

                if(doIt && line.contains(")")) {
                    String pair = ReaderUtil.stringBefore(line, ")");
                    if(pair.contains(",")) {
                        String first = ReaderUtil.stringBefore(pair, ",");
                        String second = ReaderUtil.stringAfter(pair, ",");

                        if(isValid(first) && isValid(second)) {
                            total += Integer.parseInt(first) * Integer.parseInt(second);
                        }
                    }
                }

            }

            nextMul = line.indexOf(MUL);
            nextDo = line.indexOf(DO);
            nextDont = line.indexOf(DONT);
        }

        System.out.println(total);
    }


    private static boolean isValid(String value) {
        if(value.length() < 1 || value.length() > 3) {
            return false;
        }

        for (char c : value.toCharArray()) {
            if(c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }


}
