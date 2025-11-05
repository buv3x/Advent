package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_4 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/4.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int total = 0;

        for (String row : lineInput) {
            String before = ReaderUtil.stringBefore(row, "[");
            String checksum = ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "["), "]");
            String[] split = StringUtils.split(before, "-");
            int sectorId = Integer.parseInt(split[split.length - 1]);
//            System.out.println(sectorId + " " + checksum);
            Map<Character, Integer> charCount = new HashMap<>();
            for(int i = 0; i < split.length - 1; ++i) {
                for (char c : split[i].toCharArray()) {
                    charCount.merge(c, 1, Integer::sum);
                }
            }
            System.out.println(charCount);
            List<Pair<Character, Integer>> listOfPairs = new ArrayList<>();
            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                listOfPairs.add(Pair.of(entry.getKey(), entry.getValue()));
            }

            listOfPairs.sort(Comparator.comparing(Pair<Character, Integer>::getRight).reversed().thenComparing(Pair::getLeft));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 5; ++i) {
                sb.append(listOfPairs.get(i).getLeft());
            }

            if(sb.toString().equals(checksum)) {
                total += sectorId;
            }

        }

        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        for (String row : lineInput) {
            String before = ReaderUtil.stringBefore(row, "[");
            String[] split = StringUtils.split(before, "-");
            int sectorId = Integer.parseInt(split[split.length - 1]);

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < split.length - 1; ++i) {
                for (char c : split[i].toCharArray()) {
//                    System.out.print(c);
//                    System.out.print(" ");
                    char result = (char) (((c - 'a' + sectorId) % 26) + 'a');
//                    System.out.print(result);
//                    System.out.println();
                    sb.append(result);
                }
                sb.append(' ');
            }
            System.out.print(sb);
            System.out.println(sectorId);

        }
    }




}
