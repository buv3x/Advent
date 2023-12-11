package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

public class _2015_1 {

    public static void main(String[] args) {
        String input = ReaderUtil.readInput("_2015/1.txt");
//        first(input);
        second(input);
    }

    public static void first(String input) {
        System.out.println(StringUtils.countMatches(input, '(') - StringUtils.countMatches(input, ')'));
    }

    public static void second(String input) {
        int index = 1;
        int floor = 0;
        for (char c : input.toCharArray()) {
            floor += (c == '(') ? 1 : -1;
            if(floor < 0) {
                System.out.println(index);
                break;
            }
            index++;
        }
    }

}
