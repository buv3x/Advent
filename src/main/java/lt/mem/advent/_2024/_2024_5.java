package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2024_5 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/5.txt");
//        first(input);
        second(input);
    }

    private static Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    private static List<List<Integer>> orders = new ArrayList<>();

    public static void first(List<String> input) {
        process(input, false);
    }

    public static void second(List<String> input) {
        process(input, true);
    }

    private static void process(List<String> input, boolean fix) {
        boolean order = false;
        for (String line : input) {
            if(StringUtils.isBlank(line)) {
                order = true;
            } else {
                if(order) {
                    orders.add(Arrays.stream(StringUtils.split(line, ",")).map(Integer::parseInt).collect(Collectors.toList()));
                } else {
                    int first = Integer.parseInt(ReaderUtil.stringBefore(line, "|"));
                    int second = Integer.parseInt(ReaderUtil.stringAfter(line, "|"));
                    if(!dependencies.containsKey(first)) {
                        dependencies.put(first, new HashSet<>());
                    }
                    dependencies.get(first).add(second);
                }
            }
        }

        int total = 0;

        for (List<Integer> orderValues : orders) {
            boolean valid = true;
            outerloop:
            for(int i = 0; i < orderValues.size(); ++i) {
                for (int j = i + 1; j < orderValues.size(); ++j) {
                    Set<Integer> dependencySet = dependencies.get(orderValues.get(j));
                    if(dependencySet != null && dependencySet.contains(orderValues.get(i))) {
                        valid = false;
                        break outerloop;
                    }
                }
            }
            if (valid) {
                 if(!fix) {
                    total += orderValues.get((orderValues.size() - 1) / 2);
                }
            } else {
                if(fix) {
                    LinkedList<Integer> newOrderValues = new LinkedList<>();
                    while(newOrderValues.size() < orderValues.size()) {
                        for (Integer value : orderValues) {
                            if(newOrderValues.contains(value)) {
                                continue;
                            }
                            Set<Integer> integers = dependencies.get(value);
                            if(integers == null) {
                                newOrderValues.addFirst(value);
                                break;
                            }
                            boolean validFix = true;
                            for (Integer integer : integers) {
                                if(!integer.equals(value) && orderValues.contains(integer) && !newOrderValues.contains(integer)) {
                                    validFix = false;
                                    break;
                                }
                            }
                            if(validFix) {
                                newOrderValues.addFirst(value);
                                break;
                            }
                        }
                    }
//                    System.out.println(orderValues);
//                    System.out.println(newOrderValues);
                    total += newOrderValues.get((newOrderValues.size() - 1) / 2);
                }
            }
        }


        System.out.println(total);
    }




}
