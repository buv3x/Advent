package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2015_14 {

    private static int LENGTH = 2503;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/14.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int maxTotal = 0;
        for (String line : lineInput) {
            int speed = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "can fly "), " "));
            int time = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "km/s for "), " "));
            int rest = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "rest for "), " "));

            int cycle = time + rest;
            int cycleCount = LENGTH / cycle;

            int total = cycleCount * (speed * time);

            int remainder = LENGTH - (cycle * cycleCount);
            total += speed * Math.min(remainder, time);
            if(total > maxTotal) {
                maxTotal = total;
            }
            System.out.println(total);
        }
        System.out.println();
        System.out.println(maxTotal);
    }

    public static void second(List<String> lineInput) {
        List<Deer> deers = new ArrayList<>();
        for (String line : lineInput) {
            int speed = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "can fly "), " "));
            int time = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "km/s for "), " "));
            int rest = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "rest for "), " "));
            deers.add(new Deer(speed, time, rest));
        }

        for(int i = 0; i < LENGTH; ++i) {
            for (Deer deer : deers) {
                deer.run++;
                if(deer.resting) {
                    if(deer.run >= deer.rest) {
                        deer.resting = false;
                        deer.run = 0;
                    }
                } else {
                    deer.distance += deer.speed;
                    if(deer.run >= deer.time) {
                        deer.resting = true;
                        deer.run = 0;
                    }
                }
            }

            int maxLead = 0;
            for (Deer deer : deers) {
                if(deer.distance > maxLead) {
                    maxLead = deer.distance;
                }
            }

            for (Deer deer : deers) {
                if(deer.distance == maxLead) {
                    deer.leadPoints++;
                }
            }
        }

        int maxLeadPoints = 0;
        for (Deer deer : deers) {
            if(deer.leadPoints > maxLeadPoints) {
                maxLeadPoints = deer.leadPoints;
            }
            System.out.println(deer.leadPoints);
        }


        System.out.println();
        System.out.println(maxLeadPoints);
    }

    private static class Deer {
        int speed;
        int time;
        int rest;

        boolean resting = false;
        int run = 0;
        int distance = 0;

        int leadPoints = 0;

        public Deer(int speed, int time, int rest) {
            this.speed = speed;
            this.time = time;
            this.rest = rest;
        }
    }

}
