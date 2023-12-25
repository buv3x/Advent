package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2023_24 {

    public static final double EEE = 0.0000000000000000000000000001;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/24.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void second(List<String> input) {
        List<Hail> hails = getHails(input);

        int total = 0;
        for(int i = 0; i < hails.size() - 1; ++i) {
            for(int j = i + 1; j < hails.size(); ++j) {
                Hail hail1 = hails.get(i);
                Hail hail2 = hails.get(j);
                if(compare((double) hail1.vy / hail1.vx -  (double) hail2.vy / hail2.vx, 0) &&
                        compare((double) hail1.vz / hail1.vx -  (double) hail2.vz / hail2.vx, 0)) {
                    System.out.println(i + "-" + j + ": " + "zero divisor");
                }
            }
        }
        System.out.println(total);
    }


    private static List<Hail> getHails(List<String> input) {
        List<Hail> hails = new ArrayList<>();
        for(int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            List<Long> coordinates = Arrays.stream(StringUtils.split(ReaderUtil.stringBefore(line, " @ "), ","))
                    .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            List<Integer> velocites = Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(line, " @ "), ","))
                    .map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            hails.add(new Hail(i, coordinates.get(0), coordinates.get(1), coordinates.get(2),
                    velocites.get(0), velocites.get(1), velocites.get(2)));
        }
        return hails;
    }

    private static boolean compare(double value, double comparison) {
        return Math.abs(value - comparison) < EEE;
    }

    private static class Hail {
        int index;
        long x;
        long y;
        long z;
        int vx;
        int vy;
        int vz;

        public Hail(int index, long x, long y, long z, int vx, int vy, int vz) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
        }
    }


    public static void first(List<String> input) {
        List<Hail> hails = getHails(input);

        int total = 0;
        for(int i = 0; i < hails.size() - 1; ++i) {
            for(int j = i + 1; j < hails.size(); ++j) {
                Hail hail1 = hails.get(i);
                Hail hail2 = hails.get(j);
                double divisor = (double) hail1.vy / hail1.vx -  (double) hail2.vy / hail2.vx;
                if(compare(divisor, 0)) {
                    System.out.println(i + "-" + j + ": " + "zero divisor");
                } else {
                    double x = ((hail2.y - hail1.y) + (((double) hail1.vy / hail1.vx) * hail1.x - ((double) hail2.vy / hail2.vx) * hail2.x))
                            / divisor;
                    double y = ((double) hail1.vy / hail1.vx) * x + (hail1.y - ((double) hail1.vy / hail1.vx) * hail1.x);
                    long MIN_AREA = 200000000000000L;
                    long MAX_AREA = 400000000000000L;
                    if(x + EEE < MIN_AREA || y + EEE < MIN_AREA || x - EEE > MAX_AREA || y - EEE > MAX_AREA) {
                        System.out.println(i + "-" + j + ": " + "outside");
                    } else {
                        if((x - hail1.x) * hail1.vx < 0 || (x - hail2.x) * hail2.vx < 0) {
                            System.out.println(i + "-" + j + ": " + "past");
                        } else {
                            ++total;
                            System.out.println(i + "-" + j + ": " + x + ", " + y);
                        }
                    }
                }
            }
        }
        System.out.println(total);
    }


}
