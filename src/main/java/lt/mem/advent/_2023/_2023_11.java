package lt.mem.advent._2023;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _2023_11 {

    private static int EXPANSION = 1000000;

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/11.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<String> expandedRows = new ArrayList<>();
        for (String line : input) {
            if(StringUtils.countMatches(line, '#') == 0) {
                expandedRows.add(line);
            }
            expandedRows.add(line);
        }

        List<List<Character>> expanded = new ArrayList<>();
        for (String line : expandedRows) {
            expanded.add(new ArrayList<>());
        }
        for(int i = 0; i < input.get(0).length(); ++i) {
            boolean expand = true;
            for(int j = 0; j < expandedRows.size(); ++j) {
                if(expandedRows.get(j).charAt(i) == '#') {
                    expand = false;
                    break;
                }
            }
            for(int j = 0; j < expandedRows.size(); ++j) {
                if(expand) {
                    expanded.get(j).add(expandedRows.get(j).charAt(i));
                }
                expanded.get(j).add(expandedRows.get(j).charAt(i));
            }
        }

        List<Point2D> points = new ArrayList<>();
        for(int i = 0; i < expanded.size(); ++i) {
            for(int j = 0; j < expanded.get(i).size(); ++j) {
                if(expanded.get(i).get(j) == '#') {
                    points.add(new Point2D(j, i));
                }
            }
        }

//        System.out.println(points.size());
        long total = 0;
        int pairs = 0;
        for(int i = 0; i < points.size(); ++i) {
            for(int j = i + 1; j < points.size(); ++j) {
//                System.out.println(i + " " + j);
                int distance = PointsUtil.squareDistance(points.get(i), points.get(j));
//                System.out.println(points.get(i) + " " + points.get(j) + " : " + distance);
                total += distance;
                pairs ++;
            }
        }

        System.out.println(pairs);
        System.out.println(total);

    }

    public static void second(List<String> input) {
        Set<Integer> expandedRows = new HashSet<>();
        Set<Integer> expandedColumns = new HashSet<>();

        for (int i = 0; i < input.size(); ++i) {
            if(StringUtils.countMatches(input.get(i), '#') == 0) {
                expandedRows.add(i);
            }
        }

        for(int i = 0; i < input.get(0).length(); ++i) {
            boolean expand = true;
            for(int j = 0; j < input.size(); ++j) {
                if(input.get(j).charAt(i) == '#') {
                    expand = false;
                    break;
                }
            }
            if(expand) {
                expandedColumns.add(i);
            }
        }

        List<Point2D> points = new ArrayList<>();
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                if(input.get(i).charAt(j) == '#') {
                    points.add(new Point2D(j, i));
                }
            }
        }

        System.out.println(points.size());
        System.out.println(expandedRows);
        System.out.println(expandedColumns);


        long total = 0;
        int pairs = 0;
        for(int i = 0; i < points.size(); ++i) {
            for(int j = i + 1; j < points.size(); ++j) {
                Point2D p1 = points.get(i);
                Point2D p2 = points.get(j);
                int distance = PointsUtil.squareDistance(p1, p2);

                for(int k = Math.min(p1.getX(), p2.getX()) + 1; k < Math.max(p1.getX(), p2.getX()); k++) {
                    if(expandedColumns.contains(k)) {
                        distance += EXPANSION - 1;
                    }
                }
                for(int k = Math.min(p1.getY(), p2.getY()) + 1; k < Math.max(p1.getY(), p2.getY()); k++) {
                    if(expandedRows.contains(k)) {
                        distance += EXPANSION - 1;
                    }
                }

                total += distance;
                pairs ++;
            }
        }

        System.out.println(pairs);
        System.out.println(total);

    }



}
