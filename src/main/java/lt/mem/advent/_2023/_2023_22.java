package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_22 {


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/22.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        List<Brick> bricks = processBricks(input);

        Set<Integer> uniqueSupporters = new HashSet<>();
        for (Brick brick : bricks) {
            if(brick.holdedBy.size() == 1) {
                uniqueSupporters.add(brick.holdedBy.get(0));
            }
        }

        System.out.println(bricks.size() - uniqueSupporters.size());
    }

    public static void second(List<String> input) {
        List<Brick> bricks = processBricks(input);
        bricks.sort(Comparator.comparing(b -> b.finalZ));

        long total = 0;
        for (Brick fallenBrick : bricks) {
            Set<Integer> fallenBricks = new HashSet<>();
            fallenBricks.add(fallenBrick.index);
            for (Brick brick : bricks) {
                List<Integer> holdedBy = brick.holdedBy;
                HashSet<Integer> holdedWithoutFalling = new HashSet<>(holdedBy);
                holdedWithoutFalling.removeAll(fallenBricks);
                if(holdedBy.size() > 0 && holdedWithoutFalling.size() == 0) {
                    fallenBricks.add(brick.index);
                }
            }
            System.out.println(fallenBrick.index + ": " + (fallenBricks.size() - 1));
            total += fallenBricks.size() - 1;
        }

        System.out.println(total);
    }

    private static List<Brick> processBricks(List<String> input) {
        List<Brick> bricks = new ArrayList<>();
        parseBricks(input, bricks);

        bricks.sort(Comparator.comparing(b -> b.startZ));

        List<List<Brick>> finalBricks = new ArrayList<>();
        for (Brick brick : bricks) {
            int maxIndex = Math.min(brick.startZ - 1, finalBricks.size());
            int stopIndex = 0;
            for(int i = maxIndex; i > 0; --i) {
                List<Brick> layer = finalBricks.get(i - 1);
                List<Brick> holdedBy = holdedBy(brick, layer);
                if(CollectionUtils.isNotEmpty(holdedBy)) {
                    stopIndex = i;
                    brick.holdedBy.addAll(holdedBy.stream().map(b -> b.index).collect(Collectors.toList()));
                    break;
                }
            }

            brick.finalZ = stopIndex + 1;
            for(int i = 0; i < brick.height; ++ i) {
                if (finalBricks.size() <= stopIndex + i) {
                    finalBricks.add(new ArrayList<>());
                }
                finalBricks.get(stopIndex + i).add(brick);
            }
        }
        return bricks;
    }

    private static void parseBricks(List<String> input, List<Brick> bricks) {
        int index = 0;
        for (String line : input) {
            String[] splitBefore = StringUtils.split(ReaderUtil.stringBefore(line, "~"), ",");
            String[] splitAfter = StringUtils.split(ReaderUtil.stringAfter(line, "~"), ",");

            int fromX = Math.min(Integer.parseInt(splitBefore[0]), Integer.parseInt(splitAfter[0]));
            int toX = Math.max(Integer.parseInt(splitBefore[0]), Integer.parseInt(splitAfter[0]));
            int fromY = Math.min(Integer.parseInt(splitBefore[1]), Integer.parseInt(splitAfter[1]));
            int toY = Math.max(Integer.parseInt(splitBefore[1]), Integer.parseInt(splitAfter[1]));

            int startZ = Math.min(Integer.parseInt(splitBefore[2]), Integer.parseInt(splitAfter[2]));
            int height = Math.max(Integer.parseInt(splitBefore[2]), Integer.parseInt(splitAfter[2])) - startZ + 1;

            bricks.add(new Brick(index, startZ, height, fromX, toX, fromY, toY));

            ++index;
        }
    }

    private static List<Brick> holdedBy(Brick brick, List<Brick> layer) {
        List<Brick> intersecting = new ArrayList<>();
        for (Brick layerBrick : layer) {
            if(intersects(layerBrick.fromX, brick.fromX, layerBrick.toX, brick.toX)
                    && intersects(layerBrick.fromY, brick.fromY, layerBrick.toY, brick.toY)) {
                intersecting.add(layerBrick);
            }

        }
        return intersecting;
    }

    private static boolean intersects(int from1, int from2, int to1, int to2) {
        return to2 >= from1 && to1 >= from2;
    }

    public static class Brick {

        int index;
        int startZ;
        int finalZ;
        int height;
        int fromX;
        int toX;
        int fromY;
        int toY;
        List<Integer> holdedBy = new ArrayList<>();

        public Brick(int index, int startZ, int height, int fromX, int toX, int fromY, int toY) {
            this.index = index;
            this.startZ = startZ;
            this.height = height;
            this.fromX = fromX;
            this.toX = toX;
            this.fromY = fromY;
            this.toY = toY;
        }
    }

}
