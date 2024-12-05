package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;

public class _2015_8 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/8.txt");
//                first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int difference = 0;
        for (String line : lineInput) {
            difference += 2;
            String realLine = line.substring(1, line.length() - 1);
            boolean escaped = false;
            for(int i = 0; i < realLine.length(); ++i) {
                if(escaped) {
                    if(realLine.charAt(i) == 'x') {
                        difference += 3;
                    } else {
                        difference += 1;
                    }
                    escaped = false;
                } else {
                    if(realLine.charAt(i) == '\\') {
                        escaped = true;
                    }
                }
            }
        }
        System.out.println(difference);
    }

    public static void second(List<String> lineInput) {
        int difference = 0;
        for (String line : lineInput) {
            difference += 4;
            String realLine = line.substring(1, line.length() - 1);
            for(int i = 0; i < realLine.length(); ++i) {
                if(realLine.charAt(i) == '"' || realLine.charAt(i) == '\\') {
                    difference += 1;
                }
            }
        }
        System.out.println(difference);
    }

}
