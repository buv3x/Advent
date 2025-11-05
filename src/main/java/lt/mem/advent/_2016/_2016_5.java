package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_5 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/5.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        long index = 0;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        String seed = lineInput.get(0);

        while (count < 8) {
            String value = seed + index++;
            String hex = DigestUtils.md5Hex(value);
            if(!hex.startsWith("00000")) {
                continue;
            }
            sb.append(hex.charAt(5));
            System.out.println(sb);
            count++;
        }

//        System.out.println(sb);
    }

    public static void second(List<String> lineInput) {
        long index = 0;
        Map<Integer, Character> counterMap = new TreeMap<>();
        String seed = lineInput.get(0);

        while (counterMap.size() < 8) {
            String value = seed + index++;
            String hex = DigestUtils.md5Hex(value);
            if(!hex.startsWith("00000")) {
                continue;
            }
            char positionChar = hex.charAt(5);
            if(positionChar >= '0' && positionChar <= '7') {
                int position = Integer.parseInt(positionChar + "");
                if(!counterMap.containsKey(position)) {
                    counterMap.put(position, hex.charAt(6));
                    System.out.println(counterMap);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character value : counterMap.values()) {
            sb.append(value);
        }

        System.out.println(sb);

    }




}
