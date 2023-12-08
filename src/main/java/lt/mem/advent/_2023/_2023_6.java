package lt.mem.advent._2023;

import lt.mem.advent.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class _2023_6 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/6.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<Params> params = new ArrayList<>();
        List<Integer> times = collect(input.get(0));
        List<Integer> distances = collect(input.get(1));
        for(int i = 0; i < times.size(); ++i) {
            params.add(new Params(times.get(i), distances.get(i)));
        }

        long total = 1;
        for (Params param : params) {
            BigDecimal discriminant = BigDecimal.valueOf(Math.sqrt((param.time * param.time) - (4 * param.distance)));
            BigDecimal from = BigDecimal.valueOf(param.time).subtract(discriminant)
                    .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
            BigDecimal to = BigDecimal.valueOf(param.time).add(discriminant)
                    .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
            System.out.println(from + " " + to);
            int fromInt = (int) Math.ceil(from.doubleValue() + (isIntegerValue(from) ? 1 : 0));
            int toInt = (int) Math.floor(to.doubleValue() - (isIntegerValue(from) ? 1 : 0));
            System.out.println(fromInt + " " + toInt);
            total *= toInt - fromInt + 1;
        }
        System.out.println(total);
     }


    public static void second(List<String> input) {
        ParamsBig param = new ParamsBig(collect2(input.get(0)), collect2(input.get(1)));
        System.out.println(param);

        long discriminant = param.time.multiply(param.time).subtract(param.distance.multiply(BigInteger.valueOf(4l))).longValue();
        BigDecimal from = BigDecimal.valueOf(param.time.longValue()).subtract(BigDecimal.valueOf(Math.sqrt(discriminant)))
                .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
        BigDecimal to = BigDecimal.valueOf(param.time.longValue()).add(BigDecimal.valueOf(Math.sqrt(discriminant)))
                .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
        System.out.println(from + " " + to);
        int fromInt = (int) Math.ceil(from.doubleValue() + (isIntegerValue(from) ? 1 : 0));
        int toInt = (int) Math.floor(to.doubleValue() - (isIntegerValue(from) ? 1 : 0));
        System.out.println(fromInt + " " + toInt);
        System.out.println(toInt - fromInt + 1);
    }

    private static List<Integer> collect(String input) {
        return Arrays.stream(StringUtils.split(input.substring(input.indexOf(':') + 1), ' '))
                .map(Integer::valueOf).collect(Collectors.toList());
    }

    private static BigInteger collect2(String input) {
        return new BigInteger(input.substring(input.indexOf(':') + 1).replace(" ", ""));
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.stripTrailingZeros().scale() <= 0;
    }

    private static class Params {
        int time;
        int distance;

        public Params(int time, int distance) {
            this.time = time;
            this.distance = distance;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "(" + time +
                    ", " + distance +
                    ')';
        }
    }

    private static class ParamsBig {
        BigInteger time;
        BigInteger distance;

        public ParamsBig(BigInteger time, BigInteger distance) {
            this.time = time;
            this.distance = distance;
        }

        public BigInteger getTime() {
            return time;
        }

        public void setTime(BigInteger time) {
            this.time = time;
        }

        public BigInteger getDistance() {
            return distance;
        }

        public void setDistance(BigInteger distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "(" + time +
                    ", " + distance +
                    ')';
        }
    }

}
