package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;

public class _2016_9 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/9.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        String line = lineInput.get(0);
        int counter = 0;
        int length = 0;
        while(counter < line.length()) {
            char c = line.charAt(counter);
            if(c == '(') {
                String meta = ReaderUtil.stringBefore(line.substring(counter + 1), ")");
                int sectionLength = Integer.parseInt(ReaderUtil.stringBefore(meta, "x"));
                int sectionRepeat = Integer.parseInt(ReaderUtil.stringAfter(meta, "x"));

                int startSection = counter + meta.length() + 1;
                length += Math.min(sectionLength, line.length() - startSection) * sectionRepeat;
                counter = startSection + sectionLength + 1;
            } else {
                length++;
                counter++;
            }
        }

        System.out.println(length);

    }

    public static void second(List<String> lineInput) {
        String line = lineInput.get(0);
        System.out.println(calculate(line));
    }

    private static long calculate(String line) {
        int counter = 0;
        long length = 0;
        while(counter < line.length()) {
            char c = line.charAt(counter);
            if(c == '(') {
                String meta = ReaderUtil.stringBefore(line.substring(counter + 1), ")");
                int sectionLength = Integer.parseInt(ReaderUtil.stringBefore(meta, "x"));
                int sectionRepeat = Integer.parseInt(ReaderUtil.stringAfter(meta, "x"));

                int startSection = counter + meta.length() + 1;

                length += calculate(line.substring(startSection + 1, startSection + sectionLength + 1)) * sectionRepeat;

                counter = startSection + sectionLength + 1;
            } else {
                length++;
                counter++;
            }
        }
        return length;
    }

}
