package lt.mem.advent._2025;

import lt.mem.advent.structure.Point3D;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2025_8 {

    public static final int LIMIT = 10;

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/8.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<Point3D> points = new ArrayList<>();
        for (String row : input) {
            int x = Integer.parseInt(ReaderUtil.stringBefore(row, ","));
            int y = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, ","), ","));
            int z = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringAfter(row, ","), ","));
            points.add(new Point3D(x, y, z));
        }
//        System.out.println(points);

        List<Pair<Pair<Integer, Integer>, Long>> pairs = new ArrayList<>();
        for(int i = 0; i < points.size(); ++i) {
            for(int j = i + 1; j < points.size(); ++j) {
                Pair<Integer, Integer> pairPoint = Pair.of(i, j);
                long distance = ((long) (points.get(i).getX() - points.get(j).getX()) * (points.get(i).getX() - points.get(j).getX())) +
                        ((long) (points.get(i).getY() - points.get(j).getY()) * (points.get(i).getY() - points.get(j).getY())) +
                        ((long) (points.get(i).getZ() - points.get(j).getZ()) * (points.get(i).getZ() - points.get(j).getZ()));

                Pair<Pair<Integer, Integer>, Long> pair = Pair.of(pairPoint, distance);
                pairs.add(pair);
            }
        }
        pairs.sort(Comparator.comparing(Pair::getRight));

//        System.out.println(pairs);
        Map<Integer, Set<Integer>> circuits = new HashMap<>();
        for (int i = 0; i < points.size(); ++i) {
            Set<Integer> single = new HashSet<>();
            single.add(i);
            circuits.put(i, single);
        }

        int counter = 0;
        int connections = 0;
        while (counter < LIMIT) {
            Pair<Pair<Integer, Integer>, Long> pair = pairs.get(counter);
            Integer leftPoint = pair.getLeft().getLeft();
            Integer rightPoint = pair.getLeft().getRight();
            if(!circuits.get(leftPoint).contains(rightPoint)) {
                circuits.get(leftPoint).addAll(circuits.get(rightPoint));
                for (Integer index : circuits.get(leftPoint)) {
                    circuits.put(index, circuits.get(leftPoint));
                }
                connections++;
            }
            counter++;

//            System.out.println(circuits);
        }

        List<Set<Integer>> circuitsTable = new ArrayList<>();
        Set<Integer> processedPoints = new HashSet<>();
        for (int i = 0; i < points.size(); ++i) {
            if(processedPoints.contains(i)) {
                continue;
            }
            circuitsTable.add(circuits.get(i));
            processedPoints.addAll(circuits.get(i));
        }

        circuitsTable.sort(Comparator.comparing(Set::size));
        System.out.println(circuitsTable);
        for (Set<Integer> set : circuitsTable) {
            System.out.println(set.size());
        }

        System.out.println(circuitsTable.get(circuitsTable.size() - 1).size() *
                circuitsTable.get(circuitsTable.size() - 2).size() *
                circuitsTable.get(circuitsTable.size() - 3).size());

    }

    public static void second(List<String> input) {
        List<Point3D> points = new ArrayList<>();
        for (String row : input) {
            int x = Integer.parseInt(ReaderUtil.stringBefore(row, ","));
            int y = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, ","), ","));
            int z = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringAfter(row, ","), ","));
            points.add(new Point3D(x, y, z));
        }
//        System.out.println(points);

        List<Pair<Pair<Integer, Integer>, Long>> pairs = new ArrayList<>();
        for(int i = 0; i < points.size(); ++i) {
            for(int j = i + 1; j < points.size(); ++j) {
                Pair<Integer, Integer> pairPoint = Pair.of(i, j);
                long distance = ((long) (points.get(i).getX() - points.get(j).getX()) * (points.get(i).getX() - points.get(j).getX())) +
                        ((long) (points.get(i).getY() - points.get(j).getY()) * (points.get(i).getY() - points.get(j).getY())) +
                        ((long) (points.get(i).getZ() - points.get(j).getZ()) * (points.get(i).getZ() - points.get(j).getZ()));

                Pair<Pair<Integer, Integer>, Long> pair = Pair.of(pairPoint, distance);
                pairs.add(pair);
            }
        }
        pairs.sort(Comparator.comparing(Pair::getRight));

//        System.out.println(pairs);
        Map<Integer, Set<Integer>> circuits = new HashMap<>();
        for (int i = 0; i < points.size(); ++i) {
            Set<Integer> single = new HashSet<>();
            single.add(i);
            circuits.put(i, single);
        }

        int counter = 0;
        while (true) {
            Pair<Pair<Integer, Integer>, Long> pair = pairs.get(counter);
            Integer leftPoint = pair.getLeft().getLeft();
            Integer rightPoint = pair.getLeft().getRight();
            if(!circuits.get(leftPoint).contains(rightPoint)) {
                circuits.get(leftPoint).addAll(circuits.get(rightPoint));
                for (Integer index : circuits.get(leftPoint)) {
                    circuits.put(index, circuits.get(leftPoint));
                }

                if(circuits.get(leftPoint).size() == points.size()) {
                    System.out.println(leftPoint);
                    System.out.println(rightPoint);
                    System.out.println((long) points.get(leftPoint).getX() * points.get(rightPoint).getX());
                    break;
                }
            }
            counter++;

        }

    }


}
