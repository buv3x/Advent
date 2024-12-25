package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class _2024_22 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/22.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<Long> values = input.stream().map(Long::new).collect(Collectors.toList());

        long total = 0;

        for (Long value : values) {
            long secret = value;
            for(int i = 0; i < 2000; ++i) {
                secret = recalculate(secret);
            }
            total += secret;
        }

        System.out.println(total);

    }

    private static long recalculate(long secret) {
        long prel = secret * 64;
        secret = (secret ^ prel) % 16777216;
//            prel = BigDecimal.valueOf(secret).divide(new BigDecimal(32), RoundingMode.HALF_UP).longValue();

        prel = secret / 32;

        secret = (secret ^ prel) % 16777216;
        prel = secret * 2048;
        secret = (secret ^ prel) % 16777216;
        return secret;
    }

    private static final Map<String, Map<Integer, Integer>> cache = new HashMap<>();

    public static void second(List<String> input) {
        List<Long> values = input.stream().map(Long::new).collect(Collectors.toList());

        for (int i = 0; i < values.size(); ++i) {
            long secret = values.get(i);
            LinkedList<Integer> sequence = new LinkedList<>();
            int oldPrice = (int) (secret % 10);
            for(int j = 0; j < 2000; ++j) {
                secret = recalculate(secret);
                int secretPrice = (int) (secret % 10);
                int change = secretPrice - oldPrice;
                sequence.addLast(change);
                if(sequence.size() > 4) {
                    sequence.removeFirst();
                }

                if(sequence.size() == 4) {
                    String key = StringUtils.join(sequence, ',');
                    if(!cache.containsKey(key)) {
                        cache.put(key, new HashMap<>());
                    }
                    Map<Integer, Integer> valueCache = cache.get(key);
                    if(!valueCache.containsKey(i)) {
                        valueCache.put(i, secretPrice);
                    }
                }
                oldPrice = secretPrice;
            }
        }

        int maxTotal = 0;
        for (Map.Entry<String, Map<Integer, Integer>> entry : cache.entrySet()) {
            Map<Integer, Integer> value = entry.getValue();
            int sum = value.values().stream().mapToInt(x -> x).sum();
            if(sum > maxTotal) {
//                System.out.println(entry.getKey() + " " + sum);
                maxTotal = sum;
            }
        }

        System.out.println(maxTotal);

    }





}
