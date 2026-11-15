package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2025_12 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/12_ex2.txt");
        long timeMillis = System.currentTimeMillis();
        first(input);
//        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<Grid> grids = new ArrayList<>();
        int counter = 0;
        for (String row : input) {
            Grid grid = new Grid();
            grid.width = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringBefore(row, ":"), "x"));
            grid.height = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, ":"), "x"));
            grid.area = grid.width * grid.height;
            grid.index = counter++;
            grid.figures = Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(row, ": "), " ")).sequential()
                    .map(Integer::parseInt).collect(Collectors.toList());

            // Calculate the total number of figure units in the grid by multiplying each figure count by its weight.
            // Figure weights based on index: 0,1,2,5 => 7; 3 => 5; all others => 6.
            int total = 0;
            for (int i = 0; i < grid.figures.size(); ++i) {
                int figureCount = grid.figures.get(i);
                int weight;
                if (i == 0 || i == 1 || i == 2 || i == 5) {
                    weight = 7;
                } else if (i == 3) {
                    weight = 5;
                } else {
                    weight = 6;
                }
                total += figureCount * weight;
            }
            grid.totalFigures = total;
            grid.ratio = (double) grid.totalFigures / grid.area;
            grids.add(grid);
        }

        Collections.sort(grids, Comparator.comparing(g -> g.ratio));

        int total = 0;
        for (Grid grid : grids) {
            System.out.println(grid.index + ": " + grid.ratio);
            if(grid.ratio < 1) {
                total++;
            }
        }
        System.out.println(total);


    }

    public static void second(List<String> input) {

    }

    private static class Grid {
        int width;
        int height;
        int area;
        List<Integer> figures;
        int totalFigures;
        double ratio;
        int index;
    }

}
