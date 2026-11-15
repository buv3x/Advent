package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _2025_6 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/6.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<List<Long>> operands = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; ++i) {
            String row = input.get(i);
            List<Long> operandRow = Arrays.stream(StringUtils.split(row, ' ')).sequential().filter(StringUtils::isNotBlank)
                    .map(Long::valueOf).collect(Collectors.toList());
            operands.add(operandRow);
        }
        String operatorsRow = input.get(input.size() - 1);
        List<Character> operators = Arrays.stream(StringUtils.split(operatorsRow, ' ')).sequential().filter(StringUtils::isNotBlank)
                .map(s -> s.charAt(0)).collect(Collectors.toList());

        System.out.println(operands);
        System.out.println(operators);

        long total = 0L;
        for(int i = 0; i < operators.size(); ++i) {
            if(operators.get(i) == '+') {
                long result = 0L;
                for (List<Long> operand : operands) {
                    result += operand.get(i);
                }
                total += result;
            } else {
                long result = 1L;
                for (List<Long> operand : operands) {
                    result *= operand.get(i);
                }
                total += result;
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<List<String>> operands = new ArrayList<>();
        List<Integer> breaks = new ArrayList<>();
        outer: for(int j = 0; j < input.get(0).length(); ++j) {
            for (int i = 0; i < input.size() - 1; ++i) {
                if(input.get(i).length() <= j || input.get(i).charAt(j) != ' ') {
                    continue outer;
                }
            }
            breaks.add(j);
        }
        for (int i = 0; i < input.size() - 1; ++i) {
            operands.add(new ArrayList<>());
        }
        int previous = 0;
        for(int j = 0; j < breaks.size(); ++j) {
            for (int i = 0; i < input.size() - 1; ++i) {
                String operand = input.get(i).substring(previous, breaks.get(j));
                operands.get(i).add(operand);
            }
            previous = breaks.get(j) + 1;
        }
        for (int i = 0; i < input.size() - 1; ++i) {
            String operand = input.get(i).substring(previous);
            operands.get(i).add(operand);
        }

        String operatorsRow = input.get(input.size() - 1);
        List<Character> operators = Arrays.stream(StringUtils.split(operatorsRow, ' ')).sequential().filter(StringUtils::isNotBlank)
                .map(s -> s.charAt(0)).collect(Collectors.toList());

        for (List<String> operand : operands) {
            System.out.println(operand.size());
        }

        List<List<Long>> trueOperands = new ArrayList<>();
        for(int i = 0; i < operators.size(); ++i) {
            List<Long> trueOperand = new ArrayList<>();
            int maxLength = 0;
            for (List<String> operand : operands) {
                if(operand.get(i).length() > maxLength) {
                    maxLength = operand.get(i).length();
                }
            }
            for(int j = 0; j < maxLength; ++j) {
                StringBuilder sb = new StringBuilder();
                for (List<String> operand : operands) {
                    String iOperand = operand.get(i);
                    if(iOperand.length() > j && iOperand.charAt(j) != ' ') {
                        sb.append(iOperand.charAt(j));
                    }
                }
                trueOperand.add(Long.valueOf(sb.toString()));
            }
            trueOperands.add(trueOperand);
        }

        System.out.println(trueOperands);

        long total = 0L;
        for(int i = 0; i < operators.size(); ++i) {
            if(operators.get(i) == '+') {
                long result = 0L;
                for (Long operand : trueOperands.get(i)) {
                    result += operand;
                }
                total += result;
            } else {
                long result = 1L;
                for (Long operand : trueOperands.get(i)) {
                    result *= operand;
                }
                total += result;
            }
        }
        System.out.println(total);

    }

}
