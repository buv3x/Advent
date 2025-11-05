package lt.mem.advent._2017;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2017_14 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/14.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static int MAX_VALUE = 255;

    private final static List<Integer> tail = Arrays.asList(17, 31, 73, 47, 23);

    public static void first(List<String> lineInput) {
        String base = lineInput.get(0);

        int total = 0;
        for(int i = 0; i < 128; ++i) {
            String key = base + "-" + i;

            String bin = knotHash(key);
            total += (bin.length() - bin.replace("1", "").length());
        }

        System.out.println(total);
    }

    private static Set<Point2D> grid = new LinkedHashSet<>();

    public static void second(List<String> lineInput) {
        String base = lineInput.get(0);

        for(int i = 0; i < 128; ++i) {
            String key = base + "-" + i;
            String bin = knotHash(key);

            for (int j = 0; j < bin.length(); ++j) {
                if(bin.charAt(j) == '1') {
                    grid.add(new Point2D(j, i));
                }
            }
        }

        List<Set<Point2D>> regions = new ArrayList<>();
        Set<Point2D> usedPoints = new HashSet<>();

        for (Point2D point : grid) {
            if(usedPoints.contains(point)) {
                continue;
            }

            Set<Point2D> region = new HashSet<>();
            region.add(point);
            Set<Point2D> toProcess = new HashSet<>();
            toProcess.add(point);

            while(CollectionUtils.isNotEmpty(toProcess)) {
                Set<Point2D> newToProcess = new HashSet<>();
                for (Point2D pointToProcess : toProcess) {
                    List<Point2D> newPoints = PointsUtil.newPointsNoGrid(pointToProcess);
                    for (Point2D newPoint : newPoints) {
                        if(grid.contains(newPoint) && !region.contains(newPoint)) {
                            newToProcess.add(newPoint);
                        }
                    }

                }
                region.addAll(newToProcess);
                toProcess = newToProcess;
            }

            regions.add(region);
            usedPoints.addAll(region);
        }

        System.out.println(regions.size());

    }

    private static String knotHash(String key) {
        List<Integer> values = new ArrayList<>();
        for(int k = 0; k <= MAX_VALUE; ++k) {
            values.add(k);
        }
        List<Integer> lengths = new ArrayList<>();
        for (char c : key.toCharArray()) {
            lengths.add((int) c);
        }
        lengths.addAll(tail);

        int position = 0;
        int skip = 0;
        for(int j = 0; j < 64; ++j) {
            for (Integer length : lengths) {
                List<Integer> newValues = new ArrayList<>();
                int startPosition = position;
                int endPosition = startPosition + length - 1;
                for (int k = startPosition; k <= endPosition; ++k) {
                    newValues.add(values.get((endPosition + startPosition - k + (5 * values.size())) % values.size()));
                }
                int index = endPosition + 1;
                while (newValues.size() < values.size()) {
                    newValues.add(values.get(index % values.size()));
                    index++;
                }

                values = new ArrayList<>();
                for (int k = 0; k < newValues.size(); ++k) {
                    values.add(newValues.get((k - startPosition + (5 * newValues.size())) % newValues.size()));
                }


                position += length + skip;
                position = position % values.size();
                skip++;
            }
        }

        List<Integer> denseValues = new ArrayList<>();
        for(int k = 0; k < 16; ++k) {
            int value = 0;
            for(int j = 0; j < 16; ++j) {
                value ^= values.get((k*16) + j);
            }
            denseValues.add(value);
        }

        StringBuilder sb = new StringBuilder();
        for (Integer denseValue : denseValues) {
            sb.append(StringUtils.leftPad(Integer.toHexString(denseValue), 2, '0'));
        }
//        System.out.println(sb);
        String bin = hexToBin(sb.toString());
//        System.out.println(bin);

        return bin;
    }

    private static String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("a", "1010");
        hex = hex.replaceAll("b", "1011");
        hex = hex.replaceAll("c", "1100");
        hex = hex.replaceAll("d", "1101");
        hex = hex.replaceAll("e", "1110");
        hex = hex.replaceAll("f", "1111");
        return hex;
    }


}
