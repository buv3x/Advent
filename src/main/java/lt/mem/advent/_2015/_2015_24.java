package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _2015_24 {

    private static final List<Integer> allNumbers = new ArrayList<>();

    private static int minCount = Integer.MAX_VALUE;
    private static long minProduct = Long.MAX_VALUE;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/24.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        for (String line : lineInput) {
            allNumbers.add(Integer.parseInt(line));
        }
        allNumbers.sort(Integer::compareTo);
        Collections.reverse(allNumbers);

        int sum = allNumbers.stream().mapToInt(value -> value).sum();
        target = sum / 3;

        System.out.println(target);

        process(new ArrayList<>(), 0, false);

        System.out.println(minCount);
        System.out.println(minProduct);
    }

    private static boolean isValid = false;

    private static void process(List<Integer> selected, int index, boolean trunk) {
        if(selected.size() > minCount) {
            return;
        }
        long product = calculateProduct(selected);
        if(selected.size() == minCount) {
            if(product >= minProduct) {
                return;
            }
        }

        int sum = selected.stream().mapToInt(value -> value).sum();
        int remainder = target - sum;

        if(remainder == 0) {
            isValid = false;
            checkRest(selected, trunk);
            if(isValid) {
                if(selected.size() < minCount) {
                    minCount = selected.size();
                    minProduct = product;
                    System.out.println(selected);
                } else if (selected.size() == minCount) {
                    if(product < minProduct) {
                        minProduct = product;
                        System.out.println(selected);
                    }
                }
            }
        } else {
            if(index >= allNumbers.size()) {
                return;
            }
            Integer number = allNumbers.get(index);
            if(number <= remainder) {
                List<Integer> newSelected = new ArrayList<>(selected);
                newSelected.add(number);
                process(newSelected, index + 1, trunk);
            }
            process(selected, index + 1, trunk);
        }
    }

    private static long calculateProduct(List<Integer> selected) {
        long product = 1L;
        for (Integer number : selected) {
            product *= number;
        }
        return product;
    }

    private static List<Integer> remaining = new ArrayList<>();

    private static void checkRest(List<Integer> selected, boolean trunk) {
        remaining = new ArrayList<>();
        for (Integer number : allNumbers) {
            if(!selected.contains(number)) {
                remaining.add(number);
            }
        }

        if(!trunk) {
            processValid(new ArrayList<>(), 0);
        } else {
            processValidWithTrunk(new ArrayList<>(), new ArrayList<>(), 0);
        }

    }

    private static void processValid(List<Integer> numbers, int index) {
        if(isValid) {
            return;
        }
        int sum = numbers.stream().mapToInt(value -> value).sum();
        int remainder = target - sum;
        if(remainder == 0) {
            isValid = true;
            return;
        }
        if(index >= remaining.size()) {
            return;
        }

        Integer number = remaining.get(index);
        if(number <= remainder) {
            List<Integer> newNumbers = new ArrayList<>(numbers);
            newNumbers.add(number);
            processValid(newNumbers, index + 1);
        }
        processValid(numbers, index + 1);
    }

    private static int target = 0;

    public static void second(List<String> lineInput) {
        for (String line : lineInput) {
            allNumbers.add(Integer.parseInt(line));
        }
        allNumbers.sort(Integer::compareTo);
        Collections.reverse(allNumbers);

        int sum = allNumbers.stream().mapToInt(value -> value).sum();
        target = sum / 4;

        System.out.println(target);

        process(new ArrayList<>(), 0, true);

        System.out.println(minCount);
        System.out.println(minProduct);
    }



    private static void processValidWithTrunk(List<Integer> numbers1, List<Integer> numbers2, int index) {
        if(isValid) {
            return;
        }
        int sum1 = numbers1.stream().mapToInt(value -> value).sum();
        int remainder1 = target - sum1;
        int sum2 = numbers2.stream().mapToInt(value -> value).sum();
        int remainder2 = target - sum2;
        if(remainder1 == 0 && remainder2 == 0) {
            isValid = true;
            return;
        }
        if(index >= remaining.size()) {
            return;
        }

        Integer number = remaining.get(index);
        if(number <= remainder1) {
            List<Integer> newNumbers = new ArrayList<>(numbers1);
            newNumbers.add(number);
            processValidWithTrunk(newNumbers, numbers2, index + 1);
        }
        if(number <= remainder2) {
            List<Integer> newNumbers = new ArrayList<>(numbers2);
            newNumbers.add(number);
            processValidWithTrunk(numbers1, newNumbers, index + 1);
        }
        processValidWithTrunk(numbers1, numbers2, index + 1);
    }

}
