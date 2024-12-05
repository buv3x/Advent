package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2015_10 {


    private static Integer REPEATS = 50;


    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/10.txt");
        first(lineInput);
//        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        String line = lineInput.get(0);
        for (int i = 0; i < REPEATS; ++i) {
            StringBuilder output = new StringBuilder();
            Character currentChar = null;
            int currentPeriod = 0;
            for (int j = 0; j < line.length(); ++j) {
                if(currentChar == null) {
                    currentChar = line.charAt(j);
                    currentPeriod = 1;
                } else {
                    if(currentChar.equals(line.charAt(j))) {
                        currentPeriod++;
                    } else {
                        output.append(currentPeriod);
                        output.append(currentChar);
                        currentChar = line.charAt(j);
                        currentPeriod = 1;
                    }
                }
            }
            output.append(currentPeriod);
            output.append(currentChar);
//            System.out.println(output);
            line = output.toString();
        }
        System.out.println();
        System.out.println(line.length());

    }

    public static void second(List<String> lineInput) {
    }


}
