package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2017_06 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/6.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        String row = lineInput.get(0);
        List<Integer> values = Arrays.stream(StringUtils.split(row, ' ')).map(Integer::parseInt).collect(Collectors.toList());
        Set<String> usedKeys = new HashSet<>();
        int counter = 0;
        while(!usedKeys.contains(getKey(values))) {
            usedKeys.add(getKey(values));
            int maxIndex = 0;
            int maxValue = 0;
            for (int i = 0; i < values.size(); ++i) {
                if(values.get(i) > maxValue) {
                    maxValue = values.get(i);
                    maxIndex = i;
                }
            }

            List<Integer> newValues = new ArrayList<>(values);
            int toDistribute = newValues.get(maxIndex);
            newValues.set(maxIndex, 0);
            newValues.replaceAll(integer -> integer + toDistribute / newValues.size());
            int remainder = toDistribute % newValues.size();
            for(int i = 1; i <= remainder; ++i) {
                newValues.set((maxIndex + i) % newValues.size(), newValues.get((maxIndex + i) % newValues.size()) + 1);
            }

            values = newValues;
            counter++;
        }
        System.out.println(counter);

    }

    private static String getKey(List<Integer> values) {
        return StringUtils.join(values, '|');
    }


    public static void second(List<String> lineInput) {
        String row = lineInput.get(0);
        List<Integer> values = Arrays.stream(StringUtils.split(row, ' ')).map(Integer::parseInt).collect(Collectors.toList());
        Map<String, Integer> usedKeys = new HashMap<>();
        int counter = 0;
        while(true) {
            String key = getKey(values);
            if(usedKeys.containsKey(key)) {
                System.out.println(counter - usedKeys.get(key));
                return;
            }
            usedKeys.put(key, counter);
            int maxIndex = 0;
            int maxValue = 0;
            for (int i = 0; i < values.size(); ++i) {
                if(values.get(i) > maxValue) {
                    maxValue = values.get(i);
                    maxIndex = i;
                }
            }

            List<Integer> newValues = new ArrayList<>(values);
            int toDistribute = newValues.get(maxIndex);
            newValues.set(maxIndex, 0);
            newValues.replaceAll(integer -> integer + toDistribute / newValues.size());
            int remainder = toDistribute % newValues.size();
            for(int i = 1; i <= remainder; ++i) {
                newValues.set((maxIndex + i) % newValues.size(), newValues.get((maxIndex + i) % newValues.size()) + 1);
            }

            values = newValues;
            counter++;
        }
    }

}
