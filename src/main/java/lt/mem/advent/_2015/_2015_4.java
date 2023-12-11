package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class _2015_4 {


    public static void main(String[] args) {
        String input = ReaderUtil.readInput("_2015/4.txt");
        first(input);
//        second(input);
    }

    public static void first(String input) {
        int index = 0;
        while(true) {
            String code = input + index;
            String md5 = DigestUtils.md5Hex(code);
            if(md5.startsWith("000000")) {
                System.out.println(index);
                break;
            }
            if(index % 100000 == 0) {
                System.out.println(index);
            }
            ++index;
        }


    }

    public static void second(String input) {

    }

}
