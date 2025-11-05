package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class _2016_16 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/16.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }
    public static void first(List<String> lineInput) {
        String seed = lineInput.get(0);
        int diskSize = Integer.parseInt(lineInput.get(1));
        while(seed.length() < diskSize) {
            StringBuilder sb = new StringBuilder();
            sb.append(seed).append('0');
            for (int i = seed.length() - 1; i >= 0; --i) {
                sb.append(seed.charAt(i) == '0' ? '1' : '0');
            }
            seed = sb.toString();
//            System.out.println(seed);
        }

        System.out.println("_____");
        String value = StringUtils.left(seed, diskSize);
//        System.out.println(value);

        while(value.length() % 2 == 0) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < value.length() / 2; ++i) {
                sb.append(value.charAt(i * 2) == value.charAt((i * 2) + 1) ? '1' : '0');
            }
            value = sb.toString();
//            System.out.println(value);
        }

        System.out.println(value);

    }


    public static void second(List<String> lineInput) {

    }

}
