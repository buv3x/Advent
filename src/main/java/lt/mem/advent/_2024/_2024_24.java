package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;

public class _2024_24 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/24.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<String, Gate> allGates = new HashMap<>();
    private static final Map<String, Boolean> processedGates = new HashMap<>();


    public static void first(List<String> input) {
        fillData(input);
        BigInteger total = calculate();
        System.out.println(total);

    }

    private static BigInteger calculate() {
        Set<Gate> toProcess = new HashSet<>(allGates.values());
        int i = 0;
        while (CollectionUtils.isNotEmpty(toProcess)) {
//            System.out.println(i++ + ": " + toProcess.size());
            Set<Gate> newToProcess = new HashSet<>();
            for (Gate gate : toProcess) {
                if(processedGates.containsKey(gate.leftKey) && processedGates.containsKey(gate.rightKey)) {
                    boolean leftValue = processedGates.get(gate.leftKey);
                    boolean rightValue = processedGates.get(gate.rightKey);
                    Boolean result = gate.operation.function.apply(leftValue, rightValue);
                    processedGates.put(gate.resultKey, result);
                } else {
                    newToProcess.add(gate);
                }
            }
            if(toProcess.equals(newToProcess)) {
                return null;
            }
            toProcess = newToProcess;
        }

        Map<Integer, Boolean> zResults = new TreeMap<>();
        for (Map.Entry<String, Boolean> entry : processedGates.entrySet()) {
            if(entry.getKey().startsWith("z")) {
                int index = Integer.parseInt(entry.getKey().substring(1));
                zResults.put(index, entry.getValue());
            }
        }

        BigInteger total = BigInteger.ZERO;
        for (Map.Entry<Integer, Boolean> entry : zResults.entrySet()) {
            if(entry.getValue()) {
                total = total.add(BigInteger.valueOf(1L << entry.getKey()));
            }
        }
        return total;
    }

    private static void fillData(List<String> input) {
        boolean gateRows = false;
        for (String row : input) {
            if(StringUtils.isBlank(row)) {
                gateRows = true;
                continue;
            }
            if(!gateRows) {
                String key = ReaderUtil.stringBefore(row, ": ");
                boolean value = ReaderUtil.stringAfter(row, ": ").equals("1");
                processedGates.put(key, value);
            } else {
                String leftKey = ReaderUtil.stringBefore(row, " ");
                String rest1 = ReaderUtil.stringAfter(row, " ");
                String operationKey = ReaderUtil.stringBefore(rest1, " ");
                String rest2 = ReaderUtil.stringAfter(rest1, " ");
                String rightKey = ReaderUtil.stringBefore(rest2, " -> ");
                String resultKey = ReaderUtil.stringAfter(rest2, " -> ");
                Operation operation;
                if(operationKey.equals("OR")) {
                    operation = Operation.OR;
                } else if(operationKey.equals("AND")) {
                    operation = Operation.AND;
                } else {
                    operation = Operation.XOR;
                }
                allGates.put(resultKey, new Gate(leftKey, rightKey, resultKey, operation));
            }
        }
    }

    private static final List<Pair<String, String>> swaps = Arrays.asList(
            Pair.of("z16", "hmk"),
            Pair.of("z20", "fhp"),
            Pair.of("rvf", "tpc"),
            Pair.of("z33","fcd"));

    public static void second(List<String> input) {
        fillData(input);

        for (Pair<String, String> swap : swaps) {
            swapKeys(swap.getLeft(), swap.getRight());
        }

        Map<Integer, Set<String>> introductionMap = new TreeMap<>();
        int maxLevel = printZGates(false, introductionMap) - 1;
        System.out.println(introductionMap);

        Map<Integer, Integer> counter = doTest(maxLevel);

        System.out.println(counter);

        fixFirstIssue(introductionMap, maxLevel, counter);

        Set<String> allSwaps = new TreeSet<>();
        for (Pair<String, String> swap : swaps) {
            allSwaps.add(swap.getLeft());
            allSwaps.add(swap.getRight());
        }

        System.out.println(StringUtils.join(allSwaps, ","));
    }

    private static final int TESTS = 50;

    private static void fixFirstIssue(Map<Integer, Set<String>> introductionMap, int maxLevel, Map<Integer, Integer> counter) {
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            if(!entry.getValue().equals(TESTS)) {
                Integer level = entry.getKey();
                System.out.println("Fixing level " + level);
                Set<String> keysToSwap = introductionMap.get(level);
                for (String baseKey : keysToSwap) {
                    System.out.println("Trying key " + baseKey);
                    Set<String> potentialKeys = new HashSet<>();
                    for (Map.Entry<Integer, Set<String>> introductionEntry : introductionMap.entrySet()) {
                        if(introductionEntry.getKey() >= level) {
                            potentialKeys.addAll(introductionEntry.getValue());
                        }
                    }

                    for (String potentialKey : potentialKeys) {
                        if(potentialKey.equals(baseKey)) {
                            continue;
                        }
//                        System.out.println("Swapping with key " + potentialKey);
                        swapKeys(baseKey, potentialKey);
                        Map<Integer, Integer> potentialCounter = doTest(maxLevel);
                        if(potentialCounter.containsKey(level) && potentialCounter.get(level).equals(TESTS)) {
                            System.out.println("Successful swap: " + baseKey + " - " + potentialKey);
                        }
                        swapKeys(potentialKey, baseKey);
                    }

                }
                break;
            }
        }
    }

    private static void swapKeys(String baseKey, String potentialKey) {
        Gate baseGate = allGates.get(baseKey);
        Gate potentialGate = allGates.get(potentialKey);
        baseGate.resultKey = potentialKey;
        potentialGate.resultKey = baseKey;
        allGates.put(potentialKey, baseGate);
        allGates.put(baseKey, potentialGate);
    }

    private static Map<Integer, Integer> doTest(int maxLevel) {
        Map<Integer, Integer> counter = new TreeMap<>();
        Random random = new Random();
        for(int i = 0; i < TESTS; ++i) {
            processedGates.clear();
            BigInteger x = BigInteger.ZERO;
            BigInteger y = BigInteger.ZERO;
            for (int j = 0; j <= maxLevel; ++j) {
                boolean xValue = random.nextBoolean();
                boolean yValue = random.nextBoolean();
                if(xValue) {
                    x = x.add(BigInteger.TWO.pow(j));
                }
                if(yValue) {
                    y = y.add(BigInteger.TWO.pow(j));
                }
                String xKey = getKey('x', j);
                String yKey = getKey('y', j);
                processedGates.put(xKey, xValue);
                processedGates.put(yKey, yValue);
            }
            BigInteger expectedZ = x.add(y);

            BigInteger z = calculate();
            if(z == null) {
                return counter;
            }

//            System.out.println(expectedZ);
//            System.out.println(z);
            for (int j = 0; j <= maxLevel + 1; ++j) {
//                System.out.println(j);
                BigInteger modEx = expectedZ.mod(BigInteger.TWO);
                BigInteger modZ = z.mod(BigInteger.TWO);
                if(modEx.equals(modZ)) {
                    counter.merge(j, 1, Integer::sum);
                }
//                System.out.println(modEx.equals(modZ));
                expectedZ = expectedZ.divide(BigInteger.TWO);
                z = z.divide(BigInteger.TWO);
            }
        }
        return counter;
    }

    private static int printZGates(boolean print, Map<Integer, Set<String>> introductionMap) {
        Set<String> introduced = new HashSet<>();
        int i = 0;
        String zKey = getKey('z', i);
        while(allGates.containsKey(zKey)) {
            Set<String> introduction = new HashSet<>();
            if(print) {
                System.out.println("Level " + i);
            }
            int count = 0;
            Set<String> toProcess = new HashSet<>();
            toProcess.add(zKey);
            while(CollectionUtils.isNotEmpty(toProcess)) {
                Set<String> newToProcess = new HashSet<>();
                for (String key : toProcess) {
                    Gate gate = allGates.get(key);
                    count++;
                    if(!introduced.contains(gate.resultKey)) {
                        introduction.add(gate.resultKey);
                    }
                    if(print) {
                        System.out.print("[" + gate.leftKey + " " + gate.operation.name() + " " + gate.rightKey + " -> " + gate.resultKey + "] ");
                    }
                    if(allGates.containsKey(gate.leftKey)) {
                        newToProcess.add(gate.leftKey);
                    }
                    if(allGates.containsKey(gate.rightKey)) {
                        newToProcess.add(gate.rightKey);
                    }
                }
                toProcess = newToProcess;
            }
            if(print) {
                System.out.println();
                System.out.println("Count " + count);
                System.out.println();
            }
            introduced.addAll(introduction);
            introductionMap.put(i, introduction);
            ++i;
            zKey = getKey('z', i);
        }
        return i - 1;
    }

    private static String getKey(char letter, int i) {
        return letter + StringUtils.leftPad(String.valueOf(i), 2, '0');
    }

    private static class Gate {

        String leftKey;
        String rightKey;
        String resultKey;
        Operation operation;

        public Gate(String leftKey, String rightKey, String resultKey, Operation operation) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
            this.resultKey = resultKey;
            this.operation = operation;
        }
    }

    private enum Operation {

        OR((x,y) -> x||y),
        AND((x,y) -> x&&y),
        XOR((x,y) -> x^y);

        private final BiFunction<Boolean, Boolean, Boolean> function;

        Operation(BiFunction<Boolean, Boolean, Boolean> function) {
            this.function = function;
        }

        public BiFunction<Boolean, Boolean, Boolean> getFunction() {
            return function;
        }
    }

}
