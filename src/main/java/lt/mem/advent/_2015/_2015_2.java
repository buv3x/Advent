package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;

public class _2015_2 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/2.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int total = 0;
        for (String input : lineInput) {
            Xyz xyz = parse(input);
            int xy = xyz.x * xyz.y;
            int xz = xyz.x * xyz.z;
            int yz = xyz.y * xyz.z;
            int minSide = Math.min(yz, Math.min(xy, xz));
            int area = 2 * (xy + xz + yz) + minSide;
            System.out.println(area);
            total += area;
        }
        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        int total = 0;
        for (String input : lineInput) {
            Xyz xyz = parse(input);
            int maxSide = Math.max(xyz.x, Math.max(xyz.y, xyz.z));
            int length = 2 * (xyz.x + xyz.y + xyz.z - maxSide) + (xyz.x * xyz.y * xyz.z);
            System.out.println(length);
            total += length;
        }
        System.out.println(total);
    }

    private static Xyz parse(String input) {
        Xyz xyz = new Xyz();
        int firstX = input.indexOf('x');
        xyz.x = Integer.valueOf(input.substring(0, firstX));
        int secondX = input.indexOf('x', firstX  + 1);
        xyz.y = Integer.valueOf(input.substring(firstX + 1, secondX));
        xyz.z = Integer.valueOf(input.substring(secondX + 1));
        return xyz;
    }

    private static class Xyz {
        int x;
        int y;
        int z;
    }

}
