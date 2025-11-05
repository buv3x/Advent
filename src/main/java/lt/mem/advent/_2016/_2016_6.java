package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class _2016_6 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/6.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        List<Map<Character, Integer>> counterList = new ArrayList<>();
        for (String row : lineInput) {
            for(int i = 0; i < row.length(); ++i) {
                if(counterList.size() <= i) {
                    counterList.add(new HashMap<>());
                }

                Map<Character, Integer> map = counterList.get(i);
                map.merge(row.charAt(i), 1, Integer::sum);
            }
        }

        System.out.println(counterList);
        StringBuilder sb = new StringBuilder();
        for (Map<Character, Integer> map : counterList) {
            List<Pair<Integer, Character>> list = map.entrySet().stream().map(e -> Pair.of(e.getValue(), e.getKey())).sorted(Comparator.comparing(Pair<Integer, Character>::getKey).reversed()).collect(Collectors.toList());
            sb.append(list.get(0).getRight());
        }

        System.out.println(sb);

    }

    public static void second(List<String> lineInput) {
        List<Map<Character, Integer>> counterList = new ArrayList<>();
        for (String row : lineInput) {
            for(int i = 0; i < row.length(); ++i) {
                if(counterList.size() <= i) {
                    counterList.add(new HashMap<>());
                }

                Map<Character, Integer> map = counterList.get(i);
                map.merge(row.charAt(i), 1, Integer::sum);
            }
        }

        System.out.println(counterList);
        StringBuilder sb = new StringBuilder();
        for (Map<Character, Integer> map : counterList) {
            List<Pair<Integer, Character>> list = map.entrySet().stream().map(e -> Pair.of(e.getValue(), e.getKey())).sorted(Comparator.comparing(Pair<Integer, Character>::getKey)).collect(Collectors.toList());
            sb.append(list.get(0).getRight());
        }

        System.out.println(sb);
    }

}
