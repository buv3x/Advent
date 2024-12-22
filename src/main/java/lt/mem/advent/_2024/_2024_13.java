package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class _2024_13 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/13.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final long SHIFT = 10000000000000L;
    
    public static void first(List<String> input) {
        List<Claw> claws = fillClaws(input);

        long total = 0L;
        for (Claw claw : claws) {
            boolean possible = false;
            long minToken = Integer.MAX_VALUE;
            for(int aPresses = 0; aPresses <= 100; ++aPresses) {
                long remainingX = claw.prizeX - (aPresses * claw.ax);
                if(remainingX < 0 || remainingX % claw.bx != 0) {
                    continue;
                }
                long bPresses = remainingX / claw.bx;
                if(bPresses > 100) {
                    continue;
                }

                if((aPresses * claw.ay) + (bPresses * claw.by) != claw.prizeY) {
                    continue;
                }

                possible = true;
                long cost = (aPresses * 3) + bPresses;
                if(cost < minToken) {
                    minToken = cost;
                }
            }
            if(possible) {
                total += minToken;
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<Claw> claws = fillClaws(input);

        long total = 0;

        int i = 1;
        for (Claw claw : claws) {
            claw.prizeX += SHIFT;
            claw.prizeY += SHIFT;

            long top = claw.prizeX * claw.by - claw.prizeY * claw.bx;
            long bottom = claw.ax * claw.by - claw.ay * claw.bx;

//            System.out.println("Claw " + i + ": ");
//            System.out.println(top + " | " + bottom);
            if(top % bottom == 0) {
                long aPress = top / bottom;
                long bPress = (claw.prizeX - aPress * claw.ax) / claw.bx;
                total += (aPress * 3) + bPress;
            }
            i++;
        }


        System.out.println(total);
    }

    private static List<Claw> fillClaws(List<String> input) {
        List<Claw> claws = new ArrayList<>();
        for(int i = 0; i < (input.size() + 1) / 4; ++i) {
            Claw claw = new Claw();
            String a = input.get(i * 4);
            claw.ax = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(a, "X+"), ","));
            claw.ay = Integer.parseInt(ReaderUtil.stringAfter(a, "Y+"));

            String b = input.get((i * 4) + 1);
            claw.bx = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(b, "X+"), ","));
            claw.by = Integer.parseInt(ReaderUtil.stringAfter(b, "Y+"));

            String prize = input.get((i * 4) + 2);
            claw.prizeX = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(prize, "X="), ","));
            claw.prizeY = Integer.parseInt(ReaderUtil.stringAfter(prize, "Y="));
            claws.add(claw);
        }
        return claws;
    }



    private static class Claw {
        long ax;
        long bx;
        long ay;
        long by;
        long prizeX;
        long prizeY;
    }


}
