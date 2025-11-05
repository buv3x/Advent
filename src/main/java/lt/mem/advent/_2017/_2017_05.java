package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _2017_05 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/5.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        List<Integer> jumps = new ArrayList<>();
        for (String row : lineInput) {
            jumps.add(Integer.parseInt(row));
        }
        int counter = 0;
        int index = 0;
        while(index >= 0 && index < jumps.size()) {
            Integer jump = jumps.get(index);
            jumps.set(index, jump + 1);
            index += jump;
            counter++;
        }
        System.out.println(counter);
    }

    public static void second(List<String> lineInput) {
        List<Integer> jumps = new ArrayList<>();
        for (String row : lineInput) {
            jumps.add(Integer.parseInt(row));
        }
        int counter = 0;
        int index = 0;
        while(index >= 0 && index < jumps.size()) {
            Integer jump = jumps.get(index);
            jumps.set(index, jump >= 3 ? jump - 1 : jump + 1);
            index += jump;
            counter++;
        }
        System.out.println(counter);
    }

}
