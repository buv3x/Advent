package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2024_19 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/19.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Set<String> towels = new HashSet<>();
    private static int maxTowelLength = 0;

    public static void first(List<String> input) {
        for(String towel : StringUtils.split(input.get(0), ", ")) {
            towels.add(towel);
            maxTowelLength = Math.max(maxTowelLength, towel.length());
        }

        List<String> targets = new ArrayList<>();
        for (int i = 2; i < input.size(); ++i) {
            targets.add(input.get(i));
        }

        int total = 0;
        for (String target : targets) {
            unProcessable.clear();
            if(process(target)) {
                System.out.println(target);
                total++;
            }
        }

        System.out.println(total);

    }

    private final static Set<String> unProcessable = new HashSet<>();

    private static boolean process(String toProcess) {
        if(unProcessable.contains(toProcess)) {
            return false;
        }
        if(toProcess.length() <= maxTowelLength && towels.contains(toProcess)) {
            return true;
        }
        for(int i = Math.min(maxTowelLength, toProcess.length() - 1); i >= 1 ; i--) {
            String toCheck = toProcess.substring(0, i);
            if(towels.contains(toCheck)) {
                String newToProcess = toProcess.substring(i);
                boolean process = process(newToProcess);
                if(process) {
                    return true;
                } else {
                    unProcessable.add(newToProcess);
                }
            }
        }
        return false;
    }

    private final static Map<String, Long> processed = new HashMap<>();

    public static void second(List<String> input) {
        for(String towel : StringUtils.split(input.get(0), ", ")) {
            towels.add(towel);
            maxTowelLength = Math.max(maxTowelLength, towel.length());
        }

        List<String> targets = new ArrayList<>();
        for (int i = 2; i < input.size(); ++i) {
            targets.add(input.get(i));
        }

        long total = 0;
        for (String target : targets) {
            processed.clear();
            total += process2(target);
        }

        System.out.println(total);
    }

    private static long process2(String toProcess) {
        if(processed.containsKey(toProcess)) {
            return processed.get(toProcess);
        }
        long total = 0;
        for(int i = Math.min(maxTowelLength, toProcess.length()); i >= 1 ; i--) {
            String toCheck = toProcess.substring(0, i);
            if(towels.contains(toCheck)) {
                String newToProcess = toProcess.substring(i);
                if(i == toProcess.length() && towels.contains(toProcess)) {
                    total++;
                }
                long process = process2(newToProcess);
                total += process;
                processed.put(newToProcess, process);
            }
        }
        return total;
    }

}
