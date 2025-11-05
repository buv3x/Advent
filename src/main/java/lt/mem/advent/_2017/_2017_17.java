package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2017_17 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/17.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        int length = Integer.parseInt(lineInput.get(0));
        List<Integer> values = new ArrayList<>();
        values.add(0);
        int position = 0;
        for(int i = 1; i <= 2017; ++i) {
            position = (position + length) % values.size();
            values.add(++position, i);
        }

        for(int i = 0; i < values.size(); ++i) {
            if(values.get(i).equals(2017)) {
                System.out.println(values.get(i == values.size() - 1 ? 0 : i + 1));
            }
        }
    }

    public static void second(List<String> lineInput) {
        int length = Integer.parseInt(lineInput.get(0));
        int totalSize = 1;
        int position = 0;
        int nextValue = 0;
        for(int i = 1; i <= 50000000; ++i) {
            position = (position + length) % totalSize;
            totalSize++;
            if(position == 0) {
                nextValue = i;
                System.out.println(nextValue);
            }
            position++;
        }

        System.out.println(nextValue);
    }


}
