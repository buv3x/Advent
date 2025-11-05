package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2017_11 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/11.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        HexVector vector = new HexVector();
        for (String code : StringUtils.split(lineInput.get(0), ',')) {
            if(code.equals("n")) {
                vector.north++;
            } else if(code.equals("s")) {
                vector.north--;
            } else if(code.equals("se")) {
                vector.sEast++;
            } else if(code.equals("nw")) {
                vector.sEast--;
            } else if(code.equals("sw")) {
                vector.sWest++;
            } else {
                vector.sWest--;
            }
        }

        System.out.println("N: " + vector.north + ". SE: " + vector.sEast + ". SW: " + vector.sWest);
        System.out.println(calculate(vector));

    }

    private static int calculate(HexVector hexVector) {
        hexVector.sWest -= hexVector.north;
        hexVector.sEast -= hexVector.north;
        hexVector.north = 0;
        if(hexVector.sWest * hexVector.sEast > 0) {
            return Math.max(Math.abs(hexVector.sEast),Math.abs(hexVector.sWest));
        } else {
            return Math.abs(hexVector.sEast) + Math.abs(hexVector.sWest);
        }

    }

    public static void second(List<String> lineInput) {
        HexVector vector = new HexVector();
        int maxDistance = 0;
        for (String code : StringUtils.split(lineInput.get(0), ',')) {
            if(code.equals("n")) {
                vector.north++;
            } else if(code.equals("s")) {
                vector.north--;
            } else if(code.equals("se")) {
                vector.sEast++;
            } else if(code.equals("nw")) {
                vector.sEast--;
            } else if(code.equals("sw")) {
                vector.sWest++;
            } else {
                vector.sWest--;
            }
            int dist = calculate(vector);
            if(dist > maxDistance) {
                maxDistance = dist;
            }
        }

        System.out.println(maxDistance);

//        System.out.println("N: " + vector.north + ". SE: " + vector.sEast + ". SW: " + vector.sWest);
//        System.out.println(calculate(vector));
    }

    private static class HexVector {
        int north;
        int sWest;
        int sEast;
    }

}
