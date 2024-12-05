package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _2024_2 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/2.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        process(input, false);
    }

    public static void second(List<String> input) {
        process(input, true);
    }

    private static void process(List<String> input, boolean damp) {
        int total = 0;
        for (String line : input) {
            List<String> row = Arrays.asList(StringUtils.split(line, ' '));
            boolean valid = isValid(row);
            if(valid) {
                total++;
            } else {
                if(damp) {
                    for(int i = 0; i < row.size(); ++i) {
                        List<String> newRow = new ArrayList<>(row);
                        newRow.remove(i);
                        boolean newValid = isValid(newRow);
                        if(newValid) {
                            total++;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(total);
    }

    private static boolean isValid(List<String> row) {
        boolean valid = true;
        Boolean up = null;
        Integer previousValue = null;
        for (String rowValue : row) {
            Integer value = Integer.parseInt(rowValue);
            if(previousValue != null) {
                int diff = Math.abs(value - previousValue);
                if (diff < 1 || diff > 3) {
                    valid = false;
                    break;
                }
                if (up != null) {
                    if (up) {
                        if (value < previousValue) {
                            valid = false;
                            break;
                        }
                    } else {
                        if (value > previousValue) {
                            valid = false;
                            break;
                        }
                    }
                } else {
                    up = value > previousValue;
                }
            }
            previousValue = value;
        }
        return valid;
    }

}
