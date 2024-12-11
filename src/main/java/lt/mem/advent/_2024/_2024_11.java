package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class _2024_11 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/11_ex2.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
//        second(input);
        test(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final BigInteger _2024 = BigInteger.valueOf(2024);
    
    public static void first(List<String> input) {
        process(input, 25);
    }

    public static void second(List<String> input) {
        process2(input, 150);
    }

    public static void test(List<String> input) {
        List<BigInteger> initialValues = Arrays.stream(StringUtils.split(input.get(0), " ")).map(BigInteger::new)
                .collect(Collectors.toList());

        Map<BigInteger, BigInteger> values = new HashMap<>();
        for (BigInteger initialValue : initialValues) {
            if(!values.containsKey(initialValue)) {
                values.put(initialValue, BigInteger.ONE);
            } else {
                values.put(initialValue, values.get(initialValue).add(BigInteger.ONE));
            }
        }
//        System.out.println(values);

        Set<BigInteger> visited = new TreeSet<>();
        visited.addAll(values.keySet());

        for(int i = 0; i < 150; i++) {
            Map<BigInteger, BigInteger> newValues = new HashMap<>();
            for (BigInteger value : values.keySet()) {
                if(value.equals(BigInteger.ZERO)) {
                    BigInteger newValue = BigInteger.ONE;
                    if(!newValues.containsKey(newValue)) {
                        newValues.put(newValue, BigInteger.ZERO);
                    }
                    newValues.put(newValue, newValues.get(newValue).add(values.get(value)));
                } else if(value.toString().length() % 2 == 0) {
                    String stringToSplit = value.toString();
                    BigInteger newValue1 = new BigInteger(stringToSplit.substring(0, stringToSplit.length() / 2));
                    if(!newValues.containsKey(newValue1)) {
                        newValues.put(newValue1, BigInteger.ZERO);
                    }
                    newValues.put(newValue1, newValues.get(newValue1).add(values.get(value)));
                    BigInteger newValue2 = new BigInteger(stringToSplit.substring(stringToSplit.length() / 2));
                    if(!newValues.containsKey(newValue2)) {
                        newValues.put(newValue2, BigInteger.ZERO);
                    }
                    newValues.put(newValue2, newValues.get(newValue2).add(values.get(value)));
                } else {
                    BigInteger newValue = value.multiply(_2024);
                    if(!newValues.containsKey(newValue)) {
                        newValues.put(newValue, BigInteger.ZERO);
                    }
                    newValues.put(newValue, newValues.get(newValue).add(values.get(value)));
                }
            }
            values = newValues;
            visited.addAll(values.keySet());
//            System.out.println(i + ": " + values.size());
        }

        System.out.println(visited.size());
        System.out.println(visited);
    }

    private static void process2(List<String> input, int steps) {
        List<BigInteger> initialValues = Arrays.stream(StringUtils.split(input.get(0), " ")).map(BigInteger::new)
                .collect(Collectors.toList());

        Map<BigInteger, BigInteger> values = new HashMap<>();
        for (BigInteger initialValue : initialValues) {
            if(!values.containsKey(initialValue)) {
                values.put(initialValue, BigInteger.ONE);
            } else {
                values.put(initialValue, values.get(initialValue).add(BigInteger.ONE));
            }
        }
//        System.out.println(values);

        for(int i = 0; i < steps; i++) {
            Map<BigInteger, BigInteger> newValues = new HashMap<>();
            for (BigInteger value : values.keySet()) {
                if(value.equals(BigInteger.ZERO)) {
                    BigInteger newValue = BigInteger.ONE;
                    if(!newValues.containsKey(newValue)) {
                        newValues.put(newValue, BigInteger.ZERO);
                    }
                    newValues.put(newValue, newValues.get(newValue).add(values.get(value)));
                } else if(value.toString().length() % 2 == 0) {
                    String stringToSplit = value.toString();
                    BigInteger newValue1 = new BigInteger(stringToSplit.substring(0, stringToSplit.length() / 2));
                    if(!newValues.containsKey(newValue1)) {
                        newValues.put(newValue1, BigInteger.ZERO);
                    }
                    newValues.put(newValue1, newValues.get(newValue1).add(values.get(value)));
                    BigInteger newValue2 = new BigInteger(stringToSplit.substring(stringToSplit.length() / 2));
                    if(!newValues.containsKey(newValue2)) {
                        newValues.put(newValue2, BigInteger.ZERO);
                    }
                    newValues.put(newValue2, newValues.get(newValue2).add(values.get(value)));
                } else {
                    BigInteger newValue = value.multiply(_2024);
                    if(!newValues.containsKey(newValue)) {
                        newValues.put(newValue, BigInteger.ZERO);
                    }
                    newValues.put(newValue, newValues.get(newValue).add(values.get(value)));
                }
            }
            values = newValues;
            System.out.println(i + ": " + values.size());
        }

        BigInteger total = BigInteger.ZERO;
        for (BigInteger bigInteger : values.values()) {
            total = total.add(bigInteger);
        }

        System.out.println(total);
    }

    private static void process(List<String> input, int steps) {
        List<BigInteger> values = Arrays.stream(StringUtils.split(input.get(0), " ")).map(BigInteger::new)
                .collect(Collectors.toList());

//        System.out.println(values);

        for(int i = 0; i < steps; i++) {
            List<BigInteger> newValues = new ArrayList<>(values.size() * 2);
            for (BigInteger value : values) {
                if(value.equals(BigInteger.ZERO)) {
                    newValues.add(BigInteger.ONE);
                } else if(value.toString().length() % 2 == 0) {
                    String stringToSplit = value.toString();
                    newValues.add(new BigInteger(stringToSplit.substring(0, stringToSplit.length() / 2)));
                    newValues.add(new BigInteger(stringToSplit.substring(stringToSplit.length() / 2)));
                } else {
                    newValues.add(value.multiply(_2024));
                }
            }
            values = newValues;
            System.out.println(i);
//            System.out.println(values);
        }

        System.out.println(values.size());
    }



}
