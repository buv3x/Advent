package lt.mem.advent._2017;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2017_04 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/4.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        int counter = 0;
        outer: for (String row : lineInput) {
            String[] s = StringUtils.split(row, ' ');
            Set<String> used = new HashSet<>();
            for (String key : s) {
                if(used.contains(key)) {
                    continue outer;
                }
                used.add(key);
            }
            counter++;
        }
        System.out.println(counter);
    }

    public static void second(List<String> lineInput) {
        int counter = 0;
        outer: for (String row : lineInput) {
            String[] s = StringUtils.split(row, ' ');
            Set<String> used = new HashSet<>();
            for (String key : s) {
                char[] cs = key.toCharArray();
                List<Character> chars = new ArrayList<>();
                for (char c : cs) {
                    chars.add(c);
                }
                chars.sort(Character::compareTo);
                StringBuilder sb = new StringBuilder();
                for (Character c : chars) {
                    sb.append(c);
                }

                if(used.contains(sb.toString())) {
                    continue outer;
                }
                used.add(sb.toString());
            }
            counter++;
        }
        System.out.println(counter);
    }

}
