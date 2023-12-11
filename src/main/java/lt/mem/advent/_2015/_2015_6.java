package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class _2015_6 {

    private final static Integer GRID_SIZE = 1000;
    private final static List<List<Boolean>> lights = new ArrayList<>();

    private final static List<List<Integer>> lights2 = new ArrayList<>();

    static {
        for(int i = 0; i < GRID_SIZE; ++i) {
            List<Boolean> list = new ArrayList<>();
            lights.add(list);
            for(int j = 0; j < GRID_SIZE; ++j) {
                list.add(false);
            }
        }

        for(int i = 0; i < GRID_SIZE; ++i) {
            List<Integer> list = new ArrayList<>();
            lights2.add(list);
            for(int j = 0; j < GRID_SIZE; ++j) {
                list.add(0);
            }
        }
    }

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/6.txt");
        first(lineInput);
//        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        for (String line : lineInput) {
            Action action;
            String coordString;
            if (line.startsWith("toggle ")) {
                action = Action.TOGGLE;
                coordString = line.substring("toggle ".length());
            } else if (line.startsWith("turn off ")) {
                action = Action.OFF;
                coordString = line.substring("turn off ".length());
            } else {
                action = Action.ON;
                coordString = line.substring("turn on ".length());
            }
            String firstC = coordString.substring(0, coordString.indexOf(" "));
            String secondC = coordString.substring(coordString.indexOf("through ") + "through ".length());
//            System.out.println(firstC);
//            System.out.println(secondC);
            int x1 = Integer.parseInt(StringUtils.split(firstC, ",")[0]);
            int y1 = Integer.parseInt(StringUtils.split(firstC, ",")[1]);
            int x2 = Integer.parseInt(StringUtils.split(secondC, ",")[0]);
            int y2 = Integer.parseInt(StringUtils.split(secondC, ",")[1]);

            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
//            System.out.println(minX + " " + minY + " " + maxX + " " + maxY);

            for (int y = minY; y <= maxY; ++y) {
                for (int x = minX; x <= maxX; ++x) {
                    lights2.get(y).set(x, action.getAction2().apply(lights2.get(y).get(x)));
                }
            }

        }

        int total = 0;
        for(int i = 0; i < GRID_SIZE; ++i) {
            for(int j = 0; j < GRID_SIZE; ++j) {
                total += lights2.get(i).get(j);
            }
        }
        System.out.println(total);


    }

    public static void second(List<String> lineInput) {
        for (String line : lineInput) {
            Action action;
            String coordString;
            if (line.startsWith("toggle ")) {
                action = Action.TOGGLE;
                coordString = line.substring("toggle ".length());
            } else if (line.startsWith("turn off ")) {
                action = Action.OFF;
                coordString = line.substring("turn off ".length());
            } else {
                action = Action.ON;
                coordString = line.substring("turn on ".length());
            }
            String firstC = coordString.substring(0, coordString.indexOf(" "));
            String secondC = coordString.substring(coordString.indexOf("through ") + "through ".length());
//            System.out.println(firstC);
//            System.out.println(secondC);
            int x1 = Integer.parseInt(StringUtils.split(firstC, ",")[0]);
            int y1 = Integer.parseInt(StringUtils.split(firstC, ",")[1]);
            int x2 = Integer.parseInt(StringUtils.split(secondC, ",")[0]);
            int y2 = Integer.parseInt(StringUtils.split(secondC, ",")[1]);

            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
//            System.out.println(minX + " " + minY + " " + maxX + " " + maxY);

            for (int y = minY; y <= maxY; ++y) {
                for (int x = minX; x <= maxX; ++x) {
                    lights.get(y).set(x, action.getAction().apply(lights.get(y).get(x)));
                }
            }

        }

        int total = 0;
        for(int i = 0; i < GRID_SIZE; ++i) {
            for(int j = 0; j < GRID_SIZE; ++j) {
                if(lights.get(i).get(j)) {
                    total++;
                }
            }
        }
        System.out.println(total);
    }

    private enum Action {
        ON(b -> true, b -> b + 1),
        OFF(b -> false, b -> Math.max(0, b - 1)),
        TOGGLE(b -> !b, b -> b + 2);

        private final Function<Boolean, Boolean> action;

        private final Function<Integer, Integer> action2;

        Action(Function<Boolean, Boolean> action, Function<Integer, Integer> action2) {
            this.action = action;
            this.action2 = action2;
        }

        public Function<Boolean, Boolean> getAction() {
            return action;
        }

        public Function<Integer, Integer> getAction2() {
            return action2;
        }
    }


}
