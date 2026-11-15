package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2017_21 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/21.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int ITERATIONS = 18;

    public static void first(List<String> lineInput) {
        Map<String, String> twosMap = new HashMap<>();
        Map<String, String> threesMap = new HashMap<>();
        for (int i = 0; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            String left = ReaderUtil.stringBefore(row, " => ").replace("/", "");
            String right = ReaderUtil.stringAfter(row, " => ").replace("/", "");
            if(left.length() == 4) {
                twosMap.put(left, right);
            } else {
                threesMap.put(left, right);
            }
        }

        Map<String, Integer> threes = new HashMap<>();
        threes.put(".#...####", 1);
        int rounds = ITERATIONS / 3;
        int remainder = ITERATIONS % 3;

        for(int i = 0; i < rounds; ++i) {
            printMaps(threes);
            Map<String, Integer> newThrees = new HashMap<>();
            for (Map.Entry<String, Integer> threeEntry : threes.entrySet()) {
                List<String> keys = getThreeKeys(threeEntry.getKey());
                String transform = "";
                for (String key : keys) {
                    if (threesMap.containsKey(key)) {
                        transform = threesMap.get(key);
                        break;
                    }
                }
                List<String> entries = getFirstStepEntries(transform);
                List<String> transformedEntries = new ArrayList<>();
                for (String entry : entries) {
                    transformedEntries.add(transformTwo(entry, twosMap));
                }

                List<String> entries2 = getSecondStepEntries(transformedEntries);
                for (String entry : entries2) {
                    String transformed = transformTwo(entry, twosMap);
                    if(!newThrees.containsKey(transformed)) {
                        newThrees.put(transformed, 0);
                    }
                    merge(newThrees, transformed, threeEntry.getValue());
                }
            }
            threes = newThrees;
        }

        printMaps(threes);

        if(remainder > 0) {
            Map<String, Integer> step1Twos = new HashMap<>();
            Map<String, Integer> step2Twos = new HashMap<>();
            for (Map.Entry<String, Integer> threeEntry : threes.entrySet()) {
                List<String> keys = getThreeKeys(threeEntry.getKey());
                String transform = "";
                for (String key : keys) {
                    if (threesMap.containsKey(key)) {
                        transform = threesMap.get(key);
                        break;
                    }
                }
                List<String> entries = getFirstStepEntries(transform);
                for (String entry : entries) {
                    if(!step1Twos.containsKey(entry)) {
                        step1Twos.put(entry, 0);
                    }
                    merge(step1Twos, entry, threeEntry.getValue());
                }

                List<String> transformedEntries = new ArrayList<>();
                for (String entry : entries) {
                    transformedEntries.add(transformTwo(entry, twosMap));
                }

                List<String> entries2 = getSecondStepEntries(transformedEntries);
                for (String entry : entries2) {
                    if(!step2Twos.containsKey(entry)) {
                        step2Twos.put(entry, 0);
                    }
                    merge(step2Twos, entry, threeEntry.getValue());
                }
            }
            if(remainder == 1) {
                printMaps(step1Twos);
            } else {
                printMaps(step2Twos);
            }

        }

    }

    private static List<String> getSecondStepEntries(List<String> transformedEntries) {
        List<String> entries2 = new ArrayList<>();
        entries2.add("" + transformedEntries.get(0).charAt(0) + transformedEntries.get(0).charAt(1) +
                transformedEntries.get(0).charAt(3) + transformedEntries.get(0).charAt(4));
        entries2.add("" + transformedEntries.get(0).charAt(2) + transformedEntries.get(1).charAt(0) +
                transformedEntries.get(0).charAt(5) + transformedEntries.get(1).charAt(3));
        entries2.add("" + transformedEntries.get(1).charAt(1) + transformedEntries.get(1).charAt(2) +
                transformedEntries.get(1).charAt(4) + transformedEntries.get(1).charAt(5));
        entries2.add("" + transformedEntries.get(0).charAt(6) + transformedEntries.get(0).charAt(7) +
                transformedEntries.get(2).charAt(0) + transformedEntries.get(2).charAt(1));
        entries2.add("" + transformedEntries.get(0).charAt(8) + transformedEntries.get(1).charAt(6) +
                transformedEntries.get(2).charAt(2) + transformedEntries.get(3).charAt(0));
        entries2.add("" + transformedEntries.get(1).charAt(7) + transformedEntries.get(1).charAt(8) +
                transformedEntries.get(3).charAt(1) + transformedEntries.get(3).charAt(2));
        entries2.add("" + transformedEntries.get(2).charAt(3) + transformedEntries.get(2).charAt(4) +
                transformedEntries.get(2).charAt(6) + transformedEntries.get(2).charAt(7));
        entries2.add("" + transformedEntries.get(2).charAt(5) + transformedEntries.get(3).charAt(3) +
                transformedEntries.get(2).charAt(8) + transformedEntries.get(3).charAt(6));
        entries2.add("" + transformedEntries.get(3).charAt(4) + transformedEntries.get(3).charAt(5) +
                transformedEntries.get(3).charAt(7) + transformedEntries.get(3).charAt(8));
        return entries2;
    }

    private static List<String> getFirstStepEntries(String transform) {
        List<String> entries = new ArrayList<>();
        entries.add("" + transform.charAt(0) + transform.charAt(1) + transform.charAt(4) + transform.charAt(5));
        entries.add("" + transform.charAt(2) + transform.charAt(3) + transform.charAt(6) + transform.charAt(7));
        entries.add("" + transform.charAt(8) + transform.charAt(9) + transform.charAt(12) + transform.charAt(13));
        entries.add("" + transform.charAt(10) + transform.charAt(11) + transform.charAt(14) + transform.charAt(15));
        return entries;
    }

    private static String transformTwo(String rotate0, Map<String, String> twosMap) {
        List<String> keys = new ArrayList<>();
        keys.add(rotate0);
        keys.add(flipTwo(rotate0));
        String rotate1 = rotateTwo(rotate0);
        keys.add(rotate1);
        keys.add(flipTwo(rotate1));
        String rotate2 = rotateTwo(rotate1);
        keys.add(rotate2);
        keys.add(flipTwo(rotate2));
        String rotate3 = rotateTwo(rotate2);
        keys.add(rotate3);
        keys.add(flipTwo(rotate3));

        String transform = "";
        for (String key : keys) {
            if(twosMap.containsKey(key)) {
                transform = twosMap.get(key);
                break;
            }
        }
        return transform;
    }

    private static List<String> getThreeKeys(String rotate0) {
        List<String> keys = new ArrayList<>();
        keys.add(rotate0);
        keys.add(flipThree(rotate0));
        String rotate1 = rotateThree(rotate0);
        keys.add(rotate1);
        keys.add(flipThree(rotate1));
        String rotate2 = rotateThree(rotate1);
        keys.add(rotate2);
        keys.add(flipThree(rotate2));
        String rotate3 = rotateThree(rotate2);
        keys.add(rotate3);
        keys.add(flipThree(rotate3));
        return keys;
    }

    private static void printMaps(Map<String, Integer> threes) {
        System.out.println(threes);
        int total = 0;
        for (Map.Entry<String, Integer> entry : threes.entrySet()) {
            total += StringUtils.countMatches(entry.getKey(), '#') * entry.getValue();
        }
        System.out.println(total);
    }

    private static void merge(Map<String, Integer> twos, String key1, int sum) {
        if(!twos.containsKey(key1)) {
            twos.put(key1, 0);
        }
        twos.merge(key1, sum, Integer::sum);
    }

    private static String rotateTwo(String rotate0) {
        return "" +
                rotate0.charAt(2) + rotate0.charAt(0) +
                rotate0.charAt(3) + rotate0.charAt(1);
    }

    private static String flipTwo(String rotate0) {
        return "" +
                rotate0.charAt(1) + rotate0.charAt(0) +
                rotate0.charAt(3) + rotate0.charAt(2);
    }

    private static String rotateThree(String rotate0) {
        return "" +
                rotate0.charAt(6) + rotate0.charAt(3) + rotate0.charAt(0) +
                rotate0.charAt(7) + rotate0.charAt(4) + rotate0.charAt(1) +
                rotate0.charAt(8) + rotate0.charAt(5) + rotate0.charAt(2);
    }

    private static String flipThree(String rotate0) {
        return "" +
                rotate0.charAt(2) + rotate0.charAt(1) + rotate0.charAt(0) +
                rotate0.charAt(5) + rotate0.charAt(4) + rotate0.charAt(3) +
                rotate0.charAt(8) + rotate0.charAt(7) + rotate0.charAt(6);
    }

    public static void second(List<String> lineInput) {


    }


}
