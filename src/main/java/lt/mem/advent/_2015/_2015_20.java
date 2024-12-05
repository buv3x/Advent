package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2015_20 {


    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/20.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int max = Integer.parseInt(lineInput.get(0)) / 10;
        int start = 2;
        int total = 0;
        while(total < max) {
            start++;
            total = 0;
            int squareRoot = Double.valueOf(Math.floor(Math.sqrt(start))).intValue();
            for(int i = 1; i <= squareRoot; ++i) {
                if(start % i == 0) {
                    total += i;
                    if(start / i != i) {
                        total += (start / i);
                    }
                }
            }
        }

        System.out.println(start);

    }

    public static void second(List<String> lineInput) {
        int max = Integer.parseInt(lineInput.get(0));
        int start = 2;
        int total = 0;
        int maxTotal = 0;
        while(total < max) {
            start++;
            total = 0;
            int squareRoot = Double.valueOf(Math.floor(Math.sqrt(start))).intValue();
            for(int i = 1; i <= squareRoot; ++i) {
                if(start % i == 0) {
                    int div1 = i;
                    int div2 = start / i;
                    if(div2 <= 50) {
                        total += div1 * 11;
                    }
                    if(div1 != div2 && div1 <= 50) {
                        total += div2 * 11;
                    }
                }
            }
//            if(total > maxTotal) {
//                maxTotal = total;
//                System.out.println(start + ": " + maxTotal);
//            }
        }

        System.out.println(start);
    }


}
