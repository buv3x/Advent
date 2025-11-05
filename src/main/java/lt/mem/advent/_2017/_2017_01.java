package lt.mem.advent._2017;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2017_01 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/1.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        String row = lineInput.get(0);
        int count = 0;
        for(int i = 0; i < row.length(); ++i) {
            if(row.charAt(i) == row.charAt((i + 1) % row.length())) {
                count += Integer.parseInt(row.charAt(i) + "");
            }
        }
        System.out.println(count);
    }

    public static void second(List<String> lineInput) {
        String row = lineInput.get(0);
        int count = 0;
        for(int i = 0; i < row.length(); ++i) {
            if(row.charAt(i) == row.charAt((i + (row.length() / 2)) % row.length())) {
                count += Integer.parseInt(row.charAt(i) + "");
            }
        }
        System.out.println(count);
    }

}
