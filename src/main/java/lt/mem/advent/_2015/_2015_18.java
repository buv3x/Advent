package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2015_18 {

    private static final int STEPS = 100;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/18.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        process(lineInput, false);
    }

    private static void process(List<String> lineInput, boolean stuck) {
        List<List<Boolean>> grid = fillGrid(lineInput, stuck);
        for(int i = 0; i < STEPS; ++i) {
            List<List<Boolean>> newGrid = new ArrayList<>();
            for(int j = 0; j < grid.size(); ++j) {
                List<Boolean> newRow = new ArrayList<>();
                for(int k = 0; k < grid.get(j).size(); ++k) {
                    int total = calculateNeighbours(grid, j, k);
                    boolean newLight = calculateNewLight(grid, j, k, total, stuck);
                    newRow.add(newLight);
                }
                newGrid.add(newRow);
            }
            grid = newGrid;
        }

        int total = 0;
        for (List<Boolean> row : grid) {
            for (Boolean light : row) {
                total += light ? 1 : 0;
            }
        }
        System.out.println(total);
    }

    private static boolean calculateNewLight(List<List<Boolean>> grid, int j, int k, int total, boolean stuck) {
        boolean light = grid.get(j).get(k);
        boolean newLight;
        if(stuck && ((j == 0 && k == 0) ||
                (j == 0 && k == grid.get(0).size() - 1) ||
                (j == grid.size() - 1 && k == 0) ||
                (j == grid.size() - 1 && k == grid.get(grid.size() - 1).size() - 1))) {
            newLight = true;
        } else {
            if (light) {
                if (total == 2 || total == 3) {
                    newLight = true;
                } else {
                    newLight = false;
                }
            } else {
                if (total == 3) {
                    newLight = true;
                } else {
                    newLight = false;
                }
            }
        }
        return newLight;
    }

    private static int calculateNeighbours(List<List<Boolean>> grid, int j, int k) {
        int total = 0;
        if(j > 0) {
            if(k > 0) {
                total += grid.get(j - 1).get(k - 1) ? 1 : 0;
            }
            total += grid.get(j - 1).get(k) ? 1 : 0;
            if(k < grid.get(j).size() - 1) {
                total += grid.get(j - 1).get(k + 1) ? 1 : 0;
            }
        }
        if(k > 0) {
            total += grid.get(j).get(k - 1) ? 1 : 0;
        }
        if(k < grid.get(j).size() - 1) {
            total += grid.get(j).get(k + 1) ? 1 : 0;
        }
        if(j < grid.size() - 1) {
            if(k > 0) {
                total += grid.get(j + 1).get(k - 1) ? 1 : 0;
            }
            total += grid.get(j + 1).get(k) ? 1 : 0;
            if(k < grid.get(j).size() - 1) {
                total += grid.get(j + 1).get(k + 1) ? 1 : 0;
            }
        }
        return total;
    }

    private static List<List<Boolean>> fillGrid(List<String> lineInput, boolean stuck) {
        List<List<Boolean>> grid = new ArrayList<>();
        for (String line : lineInput) {
            List<Boolean> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c == '#');
            }
            grid.add(row);
        }
        if(stuck) {
            grid.get(0).set(0, true);
            grid.get(0).set(grid.get(0).size() - 1, true);
            grid.get(grid.size() - 1).set(0, true);
            grid.get(grid.size() - 1).set(grid.get(grid.size() - 1).size() - 1, true);
        }
        return grid;
    }

    public static void second(List<String> lineInput) {
        process(lineInput, true);
    }

}
