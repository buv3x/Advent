package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2025_10 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/10_ex2.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<Light> lights = new ArrayList<>();
        for (String row : input) {
            Light light = new Light();
            String indicators = ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, "]"), "[");
            for (char c : indicators.toCharArray()) {
                light.targets.add(c == '#');
            }

            String switches = ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " {"), "] ");
            List<String> collects = Arrays.stream(StringUtils.split(switches, " ")).sequential()
                    .map(s -> StringUtils.strip(s, "()")).collect(Collectors.toList());
            for (String collectRow : collects) {
                light.switches.add(Arrays.stream(StringUtils.split(collectRow, ",")).sequential()
                        .map(Integer::parseInt).collect(Collectors.toList()));
            }
            lights.add(light);
        }
        System.out.println(lights);

        long total = 0;
        for (Light light : lights) {
            int combinations = 1 << light.switches.size();
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < combinations; ++i) {
                List<Boolean> toggled = new ArrayList<>();
                int divisor = i;
                for(int j = 0; j < light.switches.size(); ++j) {
                    toggled.add(divisor % 2 != 0);
                    divisor = divisor / 2;
                }
                List<Boolean> indicators = new ArrayList<>();
                for (Boolean target : light.targets) {
                    indicators.add(false);
                }
                int size = 0;
                for(int j = 0; j < toggled.size(); ++j) {
                    if(toggled.get(j)) {
                        size++;
                        for (Integer index : light.switches.get(j)) {
                            indicators.set(index, !indicators.get(index));
                        }
                    }
                }
                if(indicators.equals(light.targets)) {
                    if(size < min) {
                        min = size;
                    }
                }

            }
            total += min;
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<Joltage> joltages = new ArrayList<>();
        for (String row : input) {
            Joltage joltage = new Joltage();
            String indicators = ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, "}"), "{");
            for (String s : StringUtils.split(indicators, ",")) {
                joltage.targets.add(Integer.parseInt(s));
            }

            String switches = ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " {"), "] ");
            List<String> collects = Arrays.stream(StringUtils.split(switches, " ")).sequential()
                    .map(s -> StringUtils.strip(s, "()")).collect(Collectors.toList());
            for (String collectRow : collects) {
                joltage.switches.add(Arrays.stream(StringUtils.split(collectRow, ",")).sequential()
                        .map(Integer::parseInt).collect(Collectors.toList()));
            }
            joltage.switches.sort(Comparator.comparing(List::size));
            Collections.reverse(joltage.switches);
            for (int i = 0; i < joltage.targets.size(); ++i) {
                List<Integer> switchIndex = new ArrayList<>();
                for(int j = 0; j < joltage.switches.size(); ++j) {
                    if(joltage.switches.get(j).contains(i)) {
                        switchIndex.add(j);
                    }
                }
                joltage.switchIndexMap.put(i, switchIndex);
            }

            joltages.add(joltage);
        }

//        System.out.println(joltages);

        int total = 0;
        int counter = 0;
        for (Joltage joltage : joltages) {
            long currentTime = System.currentTimeMillis();
            Set<Integer> used = new HashSet<>();
            List<Indicator> usedIndicators = new ArrayList<>();
            while(used.size() < joltage.targets.size()) {
                List<Indicator> indicators = new ArrayList<>();
                for (int i = 0; i < joltage.targets.size(); ++i) {
                    if(used.contains(i)) {
                        continue;
                    }
                    Indicator indicator = new Indicator();
                    indicator.index = i;
                    indicator.switches = new ArrayList<>();
                    for (Integer switchIndex : joltage.switchIndexMap.get(i)) {
                        if(Collections.disjoint(joltage.switches.get(switchIndex), used)) {
                            indicator.switches.add(switchIndex);
                        }
                    }
                    indicator.target = joltage.targets.get(i);
                    indicators.add(indicator);
                }
                System.out.println(used.size() + ": " + indicators);

//                indicators.sort(Comparator.comparing(t -> t.switches.size()));
                Indicator indicator;
                if(used.size() == 0) {
                    indicator = indicators.get(7);
                } else if(used.size() == 1) {
                    indicator = indicators.get(2);
                } else {
                    indicator = indicators.get(0);
                }
                used.add(indicator.index);
                usedIndicators.add(indicator);
            }
            System.out.println(counter + ": " +
                    StringUtils.join(usedIndicators.stream().map(i -> i.switches.size()).collect(Collectors.toList()), " "));

            List<Integer> currentJoltage = new ArrayList<>();
            for (Integer integer : joltage.targets) {
                currentJoltage.add(0);
            }
            System.out.println(usedIndicators);
            int sum = calculate(joltage, 0, 0, currentJoltage, usedIndicators);
            long afterTime = System.currentTimeMillis();
            System.out.println(sum + " (" + (afterTime - currentTime) + ")");
            total += sum;
            counter++;
        }
        System.out.println(total);
    }

    private static int calculate(Joltage joltage, int sum, int position,
                                 List<Integer> currentJoltage, List<Indicator> indicators) {
        if(position >= indicators.size()) {
            if(currentJoltage.equals(joltage.targets)) {
                return sum;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < joltage.targets.size(); ++i) {
            if(currentJoltage.get(i) > joltage.targets.get(i)) {
                return Integer.MAX_VALUE;
            }
        }

        Indicator indicator = indicators.get(position);
        List<List<Integer>> switches = new ArrayList<>();
        for (Integer switchIndex : indicator.switches) {
            switches.add(joltage.switches.get(switchIndex));
        }

        int target = joltage.targets.get(indicator.index) - currentJoltage.get(indicator.index);
        if(target < 0) {
            return Integer.MAX_VALUE;
        }
        if(CollectionUtils.isEmpty(switches) || target == 0) {
            return calculate(joltage, sum, position + 1, currentJoltage, indicators);
        }

        if(switches.size() == 1) {
            List<Integer> newJoltage = new ArrayList<>(currentJoltage);
            for (Integer index : switches.get(0)) {
                newJoltage.set(index, newJoltage.get(index) + target);
            }
            return calculate(joltage, sum + target, position + 1, newJoltage, indicators);
        } else if(switches.size() == 2) {
            int minSum = Integer.MAX_VALUE;
            for(int i = 0; i <= target; i++) {
                int j = target - i;
                List<Integer> newJoltage = new ArrayList<>(currentJoltage);
                for (Integer index : switches.get(0)) {
                    newJoltage.set(index, newJoltage.get(index) + i);
                }
                for (Integer index : switches.get(1)) {
                    newJoltage.set(index, newJoltage.get(index) + j);
                }
                int calculateSum = calculate(joltage, sum + target, position + 1, newJoltage, indicators);
                if(calculateSum < minSum) {
                    minSum = calculateSum;
                }
            }
            return minSum;
        } else if(switches.size() == 3) {
            int minSum = Integer.MAX_VALUE;
            for(int i = 0; i <= target; i++) {
                for(int j = 0; j <= target - i; j++) {
                    int k = target - i - j;
                    List<Integer> newJoltage = new ArrayList<>(currentJoltage);
                    for (Integer index : switches.get(0)) {
                        newJoltage.set(index, newJoltage.get(index) + i);
                    }
                    for (Integer index : switches.get(1)) {
                        newJoltage.set(index, newJoltage.get(index) + j);
                    }
                    for (Integer index : switches.get(2)) {
                        newJoltage.set(index, newJoltage.get(index) + k);
                    }
                    int calculateSum = calculate(joltage, sum + target, position + 1, newJoltage, indicators);
                    if (calculateSum < minSum) {
                        minSum = calculateSum;
                        if(position == 0) {
                            System.out.println(minSum);
                        }
                    }
                }
            }
            return minSum;
        } else if(switches.size() == 4) {
            int minSum = Integer.MAX_VALUE;
            for(int i = 0; i <= target; i++) {
                for(int j = 0; j <= target - i; j++) {
                    for(int k = 0; k <= target - i -j; k++) {
                        int l = target - i - j - k;
                        List<Integer> newJoltage = new ArrayList<>(currentJoltage);
                        for (Integer index : switches.get(0)) {
                            newJoltage.set(index, newJoltage.get(index) + i);
                        }
                        for (Integer index : switches.get(1)) {
                            newJoltage.set(index, newJoltage.get(index) + j);
                        }
                        for (Integer index : switches.get(2)) {
                            newJoltage.set(index, newJoltage.get(index) + k);
                        }
                        for (Integer index : switches.get(3)) {
                            newJoltage.set(index, newJoltage.get(index) + l);
                        }
                        int calculateSum = calculate(joltage, sum + target, position + 1, newJoltage, indicators);
                        if (calculateSum < minSum) {
                            minSum = calculateSum;
                            if(position == 0) {
                                System.out.println(minSum);
                            }
                        }
                    }
                }
            }
            return minSum;
        } else if(switches.size() == 5) {
            int minSum = Integer.MAX_VALUE;
            for(int i = 0; i <= target; i++) {
                for(int j = 0; j <= target - i; j++) {
                    for(int k = 0; k <= target - i -j; k++) {
                        for(int l = 0; l <= target - i - j - k; l++) {
                            int p = target - i - j - k - l;
                            List<Integer> newJoltage = new ArrayList<>(currentJoltage);
                            for (Integer index : switches.get(0)) {
                                newJoltage.set(index, newJoltage.get(index) + i);
                            }
                            for (Integer index : switches.get(1)) {
                                newJoltage.set(index, newJoltage.get(index) + j);
                            }
                            for (Integer index : switches.get(2)) {
                                newJoltage.set(index, newJoltage.get(index) + k);
                            }
                            for (Integer index : switches.get(3)) {
                                newJoltage.set(index, newJoltage.get(index) + l);
                            }
                            for (Integer index : switches.get(4)) {
                                newJoltage.set(index, newJoltage.get(index) + p);
                            }
                            int calculateSum = calculate(joltage, sum + target, position + 1, newJoltage, indicators);
                            if (calculateSum < minSum) {
                                minSum = calculateSum;
                                if(position == 0) {
                                    System.out.println(minSum);
                                }
                            }
                        }
                    }
                }
            }
            return minSum;
        } else if(switches.size() == 6) {
            int minSum = Integer.MAX_VALUE;
            for(int i = 0; i <= target; i++) {
                for(int j = 0; j <= target - i; j++) {
                    for(int k = 0; k <= target - i -j; k++) {
                        for(int l = 0; l <= target - i - j - k; l++) {
                            for(int p = 0; p <= target - i - j - k - l; p++) {
                                int m = target - i - j - k - l - p;
                                List<Integer> newJoltage = new ArrayList<>(currentJoltage);
                                for (Integer index : switches.get(0)) {
                                    newJoltage.set(index, newJoltage.get(index) + i);
                                }
                                for (Integer index : switches.get(1)) {
                                    newJoltage.set(index, newJoltage.get(index) + j);
                                }
                                for (Integer index : switches.get(2)) {
                                    newJoltage.set(index, newJoltage.get(index) + k);
                                }
                                for (Integer index : switches.get(3)) {
                                    newJoltage.set(index, newJoltage.get(index) + l);
                                }
                                for (Integer index : switches.get(4)) {
                                    newJoltage.set(index, newJoltage.get(index) + p);
                                }
                                for (Integer index : switches.get(5)) {
                                    newJoltage.set(index, newJoltage.get(index) + m);
                                }
                                int calculateSum = calculate(joltage, sum + target, position + 1, newJoltage, indicators);
                                if (calculateSum < minSum) {
                                    minSum = calculateSum;
                                    if (position == 0) {
                                        System.out.println(minSum);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return minSum;
        }

        return Integer.MAX_VALUE;
    }

    private static class Indicator {
        int index;
        List<Integer> switches;
        int target;

        @Override
        public String toString() {
            return "{" +
                    "index=" + index +
                    ", switches=" + switches +
                    ", target=" + target +
                    '}';
        }
    }

    private static class Joltage {
        List<Integer> targets = new ArrayList<>();
        List<List<Integer>> switches = new ArrayList<>();
        Map<Integer, List<Integer>> switchIndexMap = new HashMap<>();

        @Override
        public String toString() {
            return "Joltage{" +
                    "targets=" + targets +
                    ", switches=" + switches +
                    ", switchIndexMap=" + switchIndexMap +
                    '}';
        }
    }

    private static class Light {
        List<Boolean> targets = new ArrayList<>();
        List<List<Integer>> switches = new ArrayList<>();

        @Override
        public String toString() {
            return "{" +
                    "targets=" + targets +
                    ", switches=" + switches +
                    '}';
        }
    }

}
