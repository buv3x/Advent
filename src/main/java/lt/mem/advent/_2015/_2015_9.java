package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2015_9 {

    private final static Map<String, Map<String, Integer>> distances = new HashMap<>();

    private static Integer minDistance = Integer.MAX_VALUE;

    private static Integer maxDistance = 0;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/9.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        readInput(lineInput);
        for (String start : distances.keySet()) {
            Holder holder = new Holder();
            holder.points = new ArrayList<>();
            holder.points.add(start);
            holder.distance = 0;
            processPoints(holder);
        }
        System.out.println(minDistance);
    }

    public static void second(List<String> lineInput) {
        readInput(lineInput);
        for (String start : distances.keySet()) {
            Holder holder = new Holder();
            holder.points = new ArrayList<>();
            holder.points.add(start);
            holder.distance = 0;
            processPointsMax(holder);
        }
        System.out.println(maxDistance);
    }

    private static void processPointsMax(Holder holder) {
        if(holder.points.size() == distances.size()) {
            if(holder.distance > maxDistance) {
                maxDistance = holder.distance;
            }
        } else {
            String start = holder.points.get(holder.points.size() - 1);
            Map<String, Integer> map = distances.get(start);
            for (String end : map.keySet()) {
                if(!holder.points.contains(end)) {
                    Holder newHolder = new Holder();
                    newHolder.points = new ArrayList<>(holder.points);
                    newHolder.points.add(end);
                    newHolder.distance = holder.distance + map.get(end);
                    processPointsMax(newHolder);
                }
            }
        }
    }

    private static void processPoints(Holder holder) {
        if(holder.distance >= minDistance) {
            return;
        }

        if(holder.points.size() == distances.size()) {
            if(holder.distance < minDistance) {
                minDistance = holder.distance;
            }
        } else {
            String start = holder.points.get(holder.points.size() - 1);
            Map<String, Integer> map = distances.get(start);
            for (String end : map.keySet()) {
                if(!holder.points.contains(end)) {
                    Holder newHolder = new Holder();
                    newHolder.points = new ArrayList<>(holder.points);
                    newHolder.points.add(end);
                    newHolder.distance = holder.distance + map.get(end);
                    processPoints(newHolder);
                }
            }
        }
    }



    private static class Holder {
        List<String> points;
        Integer distance;
    }

    private static void readInput(List<String> lineInput) {
        for (String line : lineInput) {
            String start = line.substring(0, line.indexOf(" to "));
            String end = line.substring(line.indexOf(" to ") + 4, line.indexOf(" = "));
            Integer distance = Integer.valueOf(line.substring(line.indexOf(" = ") + 3));
            if(!distances.containsKey(start)) {
                distances.put(start, new HashMap<>());
            }
            if(!distances.containsKey(end)) {
                distances.put(end, new HashMap<>());
            }
            distances.get(start).put(end, distance);
            distances.get(end).put(start, distance);
        }
    }

}
