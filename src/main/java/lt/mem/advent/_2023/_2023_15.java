package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_15 {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        String input = ReaderUtil.readInput("_2023/15.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(String input) {
        List<String> lines = Arrays.stream(StringUtils.split(input, ',')).collect(Collectors.toList());

        long total = 0;
        for (String line : lines) {
            int value = hash(line);
            total += value;
        }
        System.out.println(total);
    }

    public static void second(String input) {
        List<String> lines = Arrays.stream(StringUtils.split(input, ',')).collect(Collectors.toList());
        System.out.println(lines);
        Map<Integer, Map<String, Integer>> boxes = new HashMap<>();
        for (String line : lines) {
            String label;
            boolean remove;
            int index = -1;
            if(line.contains("=")) {
                label = ReaderUtil.stringBefore(line, "=");
                remove = false;
                index = Integer.parseInt(ReaderUtil.stringAfter(line, "="));
            } else {
                label = ReaderUtil.stringBefore(line, "-");
                remove = true;
            }

            int hash = hash(label);
            if(!boxes.containsKey(hash)) {
                boxes.put(hash, new LinkedHashMap<>());
            }
            Map<String, Integer> boxMap = boxes.get(hash);
            if(remove) {
                boxMap.remove(label);
            } else {
                boxMap.put(label, index);
            }
            System.out.println(line + " " + boxes);
        }

        long total = 0L;
        for (Map.Entry<Integer, Map<String, Integer>> entry : boxes.entrySet()) {
            int index = 1;
            for (Map.Entry<String, Integer> boxEntry : entry.getValue().entrySet()) {
                total += index * boxEntry.getValue() * (entry.getKey() + 1);
                index++;
            }
        }

        System.out.println(total);

    }

    private static int hash(String line) {
        int value = 0;
        for (char c : line.toCharArray()) {
            value += c;
            value *= 17;
            value = value % 256;
        }
        return value;
    }

}
