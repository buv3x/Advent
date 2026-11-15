package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2025_11 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/11.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<String, List<String>> graph = new HashMap<>();

    public static void first(List<String> input) {
        for (String row : input) {
            graph.put(ReaderUtil.stringBefore(row, ":"),
                    Arrays.asList(StringUtils.split(ReaderUtil.stringAfter(row, ": "), " ")));
        }
        System.out.println(graph);

        System.out.println(calculate1("you", new HashMap<>()));
    }

    private static long calculate1(String node, Map<String, Long> cache) {
        if(node.equals("out")) {
            return 1;
        }
        if(cache.containsKey(node)) {
            return cache.get(node);
        }

        long total = 0;
        for(String output : graph.get(node)) {
            total += calculate1(output, cache);
        }
        cache.put(node, total);
        return total;
    }

    public static void second(List<String> input) {
        for (String row : input) {
            graph.put(ReaderUtil.stringBefore(row, ":"),
                    Arrays.asList(StringUtils.split(ReaderUtil.stringAfter(row, ": "), " ")));
        }
        System.out.println(graph);

        System.out.println(calculate2("svr", false, false, new HashMap<>()));
    }

    private static long calculate2(String node, boolean dac, boolean fft, Map<Key, Long> cache) {
        if(node.equals("out")) {
            return dac && fft ? 1 : 0;
        }

        Key key = new Key(node, dac, fft);
        if(cache.containsKey(key)) {
            return cache.get(key);
        }

        long total = 0;
        for(String output : graph.get(node)) {
            total += calculate2(output, dac || node.equals("dac"), fft || node.equals("fft"), cache);
        }
        cache.put(key, total);
        return total;
    }

    private static class Key {
        String node;
        boolean dac;
        boolean fft;

        public Key(String node, boolean dac, boolean fft) {
            this.node = node;
            this.dac = dac;
            this.fft = fft;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return dac == key.dac && fft == key.fft && Objects.equals(node, key.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, dac, fft);
        }
    }

}
