package lt.mem.advent._2024;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.io.FileUtils;

import java.util.*;

public class _2024_14 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/14.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int X_LENGTH = 101;
    private static final int Y_LENGTH = 103;
    
    public static void first(List<String> input) {
        List<Robot> robots = fillData(input);

        for (Robot robot : robots) {
            for(int i = 0; i < 100; ++i) {
                robot.step(1);
            }
        }

        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;
        for (Robot robot : robots) {
            if(robot.x < (X_LENGTH - 1) / 2 && robot.y < (Y_LENGTH - 1) / 2) {
                topLeft++;
            }
            if(robot.x > (X_LENGTH - 1) / 2 && robot.y < (Y_LENGTH - 1) / 2) {
                topRight++;
            }
            if(robot.x < (X_LENGTH - 1) / 2 && robot.y > (Y_LENGTH - 1) / 2) {
                bottomLeft++;
            }
            if(robot.x > (X_LENGTH - 1) / 2 && robot.y > (Y_LENGTH - 1) / 2) {
                bottomRight++;
            }
        }

        System.out.println(topRight * topLeft * bottomLeft * bottomRight);

    }

    public static void second(List<String> input) {
        List<Robot> robots = fillData(input);

        int y = 0;
        while((101 * y + 51) % 103 != 0) {
            ++y;
        }

        System.out.println(y);
        int cycle = 70 + (101 * y);
        System.out.println(cycle);

        for (Robot robot : robots) {
            robot.step(cycle);
        }

//        System.out.println(0);
        Map<Point2D, Integer> points = fillPointsMap(robots);
        printGrid(points);

//        for(int i = 0; i < 5; ++i) {
//            for (Robot robot : robots) {
//                robot.step(101);
//            }
//
//            points = fillPointsMap(robots);
//            System.out.println(i + 1);
//            printGrid(points);
//        }

    }

    private static Map<Point2D, Integer> fillPointsMap(List<Robot> robots) {
        Map<Point2D, Integer> points = new HashMap<>();
        for (Robot robot : robots) {
            Point2D point2D = new Point2D(robot.x, robot.y);
            if(!points.containsKey(point2D)) {
                points.put(point2D, 1);
            } else {
                points.put(point2D, points.get(point2D) + 1);
            }
        }
        return points;
    }

    private static void printGrid(Map<Point2D, Integer> points) {
        for(int j = 0; j < Y_LENGTH; ++j) {
            for(int i = 0; i < X_LENGTH; ++i) {
                Point2D key = new Point2D(i, j);
                System.out.print(points.containsKey(key) ? (points.get(key) < 10 ? String.valueOf(points.get(key)) : '#') : '.');
            }
            System.out.println();
        }

    }

    private static List<Robot> fillData(List<String> input) {
        List<Robot> robots = new ArrayList<>();
        for (String line : input) {
            Robot robot = new Robot();
            String position = ReaderUtil.stringAfter(ReaderUtil.stringBefore(line,  " v="), "p=");
            String velocity = ReaderUtil.stringAfter(line,  " v=");
            robot.x = Integer.parseInt(ReaderUtil.stringBefore(position, ","));
            robot.y = Integer.parseInt(ReaderUtil.stringAfter(position, ","));
            robot.vx = Integer.parseInt(ReaderUtil.stringBefore(velocity, ","));
            robot.vy = Integer.parseInt(ReaderUtil.stringAfter(velocity, ","));
            robots.add(robot);
        }
        return robots;
    }




    private static class Robot {
        int x;
        int y;
        int vx;
        int vy;

        void step(int i) {
            x = (x + ((vx * i) % X_LENGTH) + X_LENGTH) % X_LENGTH;
            y = (y + ((vy * i) % Y_LENGTH) + Y_LENGTH) % Y_LENGTH;
        }
    }


}
