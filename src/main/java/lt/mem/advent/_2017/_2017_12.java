package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2017_12 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/12.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Integer, Set<Integer>> connections = new HashMap<>();

    public static void first(List<String> lineInput) {
        parse(lineInput);

//        System.out.println(connections);

        Set<Integer> processed = getProcessed(0);

        System.out.println(processed.size());

    }

    private static Set<Integer> getProcessed(int root) {
        Set<Integer> toProcess = new HashSet<>();
        Set<Integer> processed = new HashSet<>();
        toProcess.add(root);
        while(toProcess.size() > 0) {
            Set<Integer> newToProcess = new HashSet<>();
            for (Integer point : toProcess) {
                processed.add(point);
                for (Integer newPoint : connections.get(point)) {
                    if(!processed.contains(newPoint)) {
                        newToProcess.add(newPoint);
                    }
                }
            }
            toProcess = newToProcess;
        }
        return processed;
    }

    private static void parse(List<String> lineInput) {
        for (String row : lineInput) {
            int index = Integer.parseInt(ReaderUtil.stringBefore(row, " <->"));
            connections.put(index, new HashSet<>());
            List<Integer> list = Arrays.stream(StringUtils.splitByWholeSeparator(ReaderUtil.stringAfter(row, "<-> "), ", "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            for (Integer value : list) {
                if(!value.equals(index)) {
                    connections.get(index).add(value);
                }
            }
        }
    }

    public static void second(List<String> lineInput) {
        parse(lineInput);
        List<Set<Integer>> groups = new ArrayList<>();
        Set<Integer> processedIndexes = new HashSet<>();
        for (Integer index : connections.keySet()) {
            if(processedIndexes.contains(index)) {
                continue;
            }
            Set<Integer> processed = getProcessed(index);
            processedIndexes.addAll(processed);
            groups.add(processed);
        }
        System.out.println(groups.size());
    }

}
