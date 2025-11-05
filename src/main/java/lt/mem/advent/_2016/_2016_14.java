package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_14 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/14.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }
    public static void first(List<String> lineInput) {
        String salt = lineInput.get(0);
        int offset = 0;
        int counter = 0;
        Map<Integer, Character> checkMap = new HashMap<>();
        List<Pair<Integer, Integer>> properKeys = new ArrayList<>();
        while(counter < 64) {
            String value = salt + offset;
            String hex = DigestUtils.md5Hex(value);
            int checkCounter = offset;
            checkMap.entrySet().removeIf(e -> e.getKey() + 1000 < checkCounter);
            Set<Character> positives = new HashSet<>();
            for (Character character : checkMap.values()) {
                String code = String.valueOf(character).repeat(5);
                if(hex.contains(code)) {
                    positives.add(character);
                }
            }

            if(CollectionUtils.isNotEmpty(positives)) {
                Set<Integer> positiveKeys = new HashSet<>();
                for (Map.Entry<Integer, Character> entry : checkMap.entrySet()) {
                    for (Character positive : positives) {
                        if (entry.getValue().equals(positive)) {
                            positiveKeys.add(entry.getKey());
                            properKeys.add(Pair.of(entry.getKey(), offset));
                            break;
                        }
                    }
                }
                counter += positiveKeys.size();
//                System.out.println("Key " + counter + " : " + offset);
                checkMap.entrySet().removeIf(e -> positiveKeys.contains(e.getKey()));
            }

            for(int i = 0; i < hex.length() - 2; ++i) {
                if(hex.charAt(i) == hex.charAt(i + 1) && hex.charAt(i) == hex.charAt(i + 2)) {
                    checkMap.put(offset, hex.charAt(i));
                    break;
                }
            }

            offset++;
        }

        properKeys.sort(Comparator.comparing(Pair<Integer, Integer>::getKey));
        System.out.println(properKeys);
        System.out.println(properKeys.size());
        System.out.println(properKeys.get(properKeys.size() - 1).getLeft());
    }

    public static void second(List<String> lineInput) {
        String salt = lineInput.get(0);
        int offset = 0;
        int counter = 0;
        Map<Integer, Character> checkMap = new HashMap<>();
        List<Pair<Integer, Integer>> properKeys = new ArrayList<>();
        while(counter < 80) {
            String value = salt + offset;
            String hex = getHex(value);
            int checkCounter = offset;
            checkMap.entrySet().removeIf(e -> e.getKey() + 1000 < checkCounter);
            Set<Character> positives = new HashSet<>();
            for (Character character : checkMap.values()) {
                String code = String.valueOf(character).repeat(5);
                if(hex.contains(code)) {
                    positives.add(character);
                }
            }

            if(CollectionUtils.isNotEmpty(positives)) {
                Set<Integer> positiveKeys = new HashSet<>();
                for (Map.Entry<Integer, Character> entry : checkMap.entrySet()) {
                    for (Character positive : positives) {
                        if (entry.getValue().equals(positive)) {
                            positiveKeys.add(entry.getKey());
                            properKeys.add(Pair.of(entry.getKey(), offset));
                            break;
                        }
                    }
                }
                counter += positiveKeys.size();
//                System.out.println("Key " + counter + " : " + offset);
                checkMap.entrySet().removeIf(e -> positiveKeys.contains(e.getKey()));
            }

            for(int i = 0; i < hex.length() - 2; ++i) {
                if(hex.charAt(i) == hex.charAt(i + 1) && hex.charAt(i) == hex.charAt(i + 2)) {
                    checkMap.put(offset, hex.charAt(i));
                    break;
                }
            }

            offset++;
        }

        properKeys.sort(Comparator.comparing(Pair<Integer, Integer>::getKey));
        System.out.println(properKeys);
        System.out.println(properKeys.size());
        System.out.println(properKeys.get(63).getLeft());
    }

    private static String getHex(String value) {
        String hex = DigestUtils.md5Hex(value);
        for (int i = 0; i < 2016; ++i) {
            hex = DigestUtils.md5Hex(hex);
        }
        return hex;
    }

}
