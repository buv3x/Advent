package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2024_7 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/7.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        process(input, false);
    }

    public static void second(List<String> input) {
        process(input, true);
    }

    private static void process(List<String> input, boolean concat) {
        List<Pair<BigInteger, List<Integer>>> testValues = new ArrayList<>();
        for (String row : input) {
            BigInteger target = new BigInteger(ReaderUtil.stringBefore(row, ": "));
            List<Integer> operands = Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(row, ": "), " "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            testValues.add(Pair.of(target, operands));
        }

        BigInteger total = BigInteger.ZERO;
        for (Pair<BigInteger, List<Integer>> testValue : testValues) {
            List<BigInteger> operands = testValue.getValue().stream().map(BigInteger::valueOf).collect(Collectors.toList());

            if(isValid(testValue.getKey(), operands, concat)) {
                total = total.add(testValue.getKey());
            }

        }

        System.out.println(total);
    }

    private static boolean isValid(BigInteger target, List<BigInteger> operands, boolean concat) {
        if(operands.get(0).compareTo(target) > 0) {
            return false;
        }

        if(operands.size() == 1) {
            return operands.get(0).equals(target);
        }

        List<BigInteger> multOperands =  new ArrayList<>();
        multOperands.add(operands.get(0).multiply(operands.get(1)));

        List<BigInteger> sumOperands =  new ArrayList<>();
        sumOperands.add(operands.get(0).add(operands.get(1)));

        List<BigInteger> concatOperands =  new ArrayList<>();
        concatOperands.add(new BigInteger(operands.get(0).toString() + operands.get(1).toString()));

        for (int i = 2; i < operands.size(); ++i) {
            sumOperands.add(operands.get(i));
            multOperands.add(operands.get(i));
            concatOperands.add(operands.get(i));
        }

        return (concat && isValid(target, concatOperands, concat))
                || isValid(target, multOperands, concat)
                || isValid(target, sumOperands, concat)

                ;
    }

}
