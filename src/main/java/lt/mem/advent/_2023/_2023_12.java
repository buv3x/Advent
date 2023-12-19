package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_12 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/12.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<SpringState> springStates = new ArrayList<>();
        for (String line : input) {
            List<Integer> runs = Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(line, " "), ",")).map(Integer::valueOf).collect(Collectors.toList());
            List<State> states = ReaderUtil.stringBefore(line, " ").chars().mapToObj(c -> State.getByCharacter((char) c)).collect(Collectors.toList());
            springStates.add(new SpringState(states, runs));
        }

        long total = 0;
        int index = 1;
        System.out.println(springStates.size());
        for (SpringState springState : springStates) {
            long value = processSpringState(springState, 0);
            System.out.println(StringUtils.leftPad(index + "", 6, ' ') + ": " + value);
            total += value;
        }
        System.out.println(total);
    }



    public static void second(List<String> input) {
        List<SpringState> springStates = new ArrayList<>();
        for (String line : input) {
            List<Integer> runs = Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(line, " "), ",")).map(Integer::valueOf).collect(Collectors.toList());
            List<Integer> unfoldedRuns = new ArrayList<>();
            for(int i = 0; i < 5; ++i) {
                unfoldedRuns.addAll(runs);
            }
            List<State> states = ReaderUtil.stringBefore(line, " ").chars().mapToObj(c -> State.getByCharacter((char) c)).collect(Collectors.toList());
            List<State> unfoldedStates = new ArrayList<>();
            for(int i = 0; i < 4; ++i) {
                unfoldedStates.addAll(states);
                unfoldedStates.add(State.UNKNOWN);
            }
            unfoldedStates.addAll(states);
            springStates.add(new SpringState(unfoldedStates, unfoldedRuns));
        }

        long total = 0;
        int index = 1;
        long time = System.currentTimeMillis();
        System.out.println(springStates.size());
        for (SpringState springState : springStates) {
            Map<CheckResult, Long> cache = new HashMap<>();
            long value = processSpringState2(springState, cache);
            System.out.println(StringUtils.leftPad(index++ + "", 6, ' ') + ": " + value +
                    " (" + (System.currentTimeMillis() - time) + ")");
            total += value;
//            System.out.println(cache);
        }
        System.out.println(total);
    }

    private static long processSpringState2(SpringState springState, Map<CheckResult, Long> cache) {
        CheckResult checkResult = checkSpringState2(springState);
//        for (State state : springState.states) {
//            System.out.print(state.character);
//        }
//        System.out.println(" " + checkResult.valid + " (" + checkResult.newIndex + ", " + checkResult.runIndex + ", " +
//                checkResult.difference + ", " + checkResult.onRun + ")");
        if(!checkResult.valid) {
            return 0;
        }
        if(checkResult.newIndex >= springState.states.size()) {
            return 1;
        }

        if(cache.containsKey(checkResult)) {
//            System.out.println("         " + cache.get(checkResult));
            return cache.get(checkResult);
        }

        if(checkResult.onRun) {
            List<State> newStates = new ArrayList<>(springState.states);
            for(int i = 0; i < checkResult.difference; ++i) {
                if(i + checkResult.newIndex >= springState.states.size() ||
                        springState.states.get(i + checkResult.newIndex).equals(State.OFF)) {
                    cache.put(checkResult, 0L);
                    return 0;
                } else {
                    newStates.set(i + checkResult.newIndex, State.ON);
                }
            }
            if(checkResult.difference + checkResult.newIndex < springState.states.size()) {
                if(springState.states.get(checkResult.difference + checkResult.newIndex).equals(State.ON)) {
                    cache.put(checkResult, 0L);
                    return 0;
                } else {
                    newStates.set(checkResult.difference + checkResult.newIndex, State.OFF);
                }
            }
            long result = processSpringState2(new SpringState(newStates, springState.runs), cache);
            cache.put(checkResult, result);
            return result;
        } else {
            boolean invalid = false;
            long onCounter = 0;
            if(checkResult.runIndex < springState.runs.size()) {
                Integer runLength = springState.runs.get(checkResult.runIndex);
                List<State> newStates1 = new ArrayList<>(springState.states);
                for (int i = 0; i < runLength; ++i) {
                    if (i + checkResult.newIndex >= springState.states.size() ||
                            springState.states.get(i + checkResult.newIndex).equals(State.OFF)) {
                        invalid = true;
                        break;
                    } else {
                        newStates1.set(i + checkResult.newIndex, State.ON);
                    }
                }
                if (!invalid &&
                        runLength + checkResult.newIndex < springState.states.size() &&
                        !springState.states.get(runLength + checkResult.newIndex).equals(State.ON)
                        ) {
                    newStates1.set(runLength + checkResult.newIndex, State.OFF);
                }
                if (!invalid) {
                    onCounter = processSpringState2(new SpringState(newStates1, springState.runs), cache);
                }
                List<State> newStates2 = new ArrayList<>(springState.states);
                newStates2.set(checkResult.newIndex, State.OFF);
                long state2Result = processSpringState2(new SpringState(newStates2, springState.runs), cache);
                cache.put(checkResult, onCounter + state2Result);
                return onCounter + state2Result;
            } else {
                for (int i = checkResult.newIndex; i < springState.states.size(); ++i) {
                    if(springState.states.get(i).equals(State.ON)) {
                        cache.put(checkResult, 0L);
                        return 0;
                    }
                }
                cache.put(checkResult, 1L);
                return 1;
            }
        }
    }

    private static CheckResult checkSpringState2(SpringState springState) {
//        if(index == 6) {
//            int i = 0;
//            ++i;
//        }

        boolean onRun = false;
        int currentRun = 0;
        List<Integer> runs = new ArrayList<>();
        int stateIndex = 0;
        while (stateIndex < springState.states.size() && !springState.states.get(stateIndex).equals(State.UNKNOWN)) {
            State state = springState.states.get(stateIndex);
            if(state.equals(State.ON)) {
                currentRun++;
                if(!onRun) {
                    onRun = true;
                }
            } else {
                if(onRun) {
                    runs.add(currentRun);
                    onRun = false;
                    currentRun = 0;
                }
            }
            stateIndex++;
        }
        if(onRun) {
            runs.add(currentRun);
        }

        if(stateIndex == springState.states.size()) {
            if(springState.runs.size() != runs.size()) {
                return new CheckResult(false);
            }
        }

        int difference = 0;
        for(int i = 0; i < runs.size(); ++i) {
            if(springState.runs.size() <= i) {
                return new CheckResult(false);
            }
            if(i == runs.size() - 1 && onRun && stateIndex != springState.states.size()) {
                if(runs.get(i) > springState.runs.get(i)) {
                    return new CheckResult(false);
                } else if (runs.get(i) < springState.runs.get(i)) {
                    difference = springState.runs.get(i) - runs.get(i);
                }
            } else {
                if(!runs.get(i).equals(springState.runs.get(i))) {
                    return new CheckResult(false);
                }
            }
        }

        if(!onRun) {
            int minRemainder = 0;
            if(runs.size() < springState.runs.size()) {
                for(int i = runs.size(); i < springState.runs.size(); ++i) {
                    minRemainder += springState.runs.get(i);
                }
                minRemainder += springState.runs.size() - runs.size() - 1;
            }
            if(minRemainder > springState.states.size() + stateIndex) {
                return new CheckResult(false);
            }
        }

        return new CheckResult(true, runs.size(), stateIndex, difference, onRun);
    }


    private static class SpringState {
        final List<State> states;
        final List<Integer> runs;

        public SpringState(List<State> states, List<Integer> runs) {
            this.states = states;
            this.runs = runs;
        }
    }

    private enum State {
        ON('#'),
        OFF('.'),
        UNKNOWN('?');

        final Character character;

        State(Character character) {
            this.character = character;
        }

        static State getByCharacter(Character character) {
            for (State value : values()) {
                if(value.character.equals(character)) {
                    return value;
                }
            }
            return null;
        }
    }

    private static class CheckResult {
        final boolean valid;

        final Integer runIndex;

        final Integer newIndex;

        final Integer difference;

        final boolean onRun;

        public CheckResult(boolean valid, Integer runIndex, Integer newIndex, Integer difference, boolean onRun) {
            this.valid = valid;
            this.runIndex = runIndex;
            this.newIndex = newIndex;
            this.difference = difference;
            this.onRun = onRun;
        }

        public CheckResult(boolean valid) {
            this.valid = valid;
            this.runIndex = -1;
            this.newIndex = -1;
            this.difference = -1;
            this.onRun = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CheckResult that = (CheckResult) o;
            return valid == that.valid && onRun == that.onRun && Objects.equals(runIndex, that.runIndex) && Objects.equals(newIndex, that.newIndex) && Objects.equals(difference, that.difference);
        }

        @Override
        public int hashCode() {
            return Objects.hash(valid, runIndex, newIndex, difference, onRun);
        }

        @Override
        public String toString() {
            return "CheckResult{" +
                    "valid=" + valid +
                    ", runIndex=" + runIndex +
                    ", newIndex=" + newIndex +
                    ", difference=" + difference +
                    ", onRun=" + onRun +
                    '}';
        }
    }

    private static long processSpringState(SpringState springState, int index) {
        CheckResult checkResult = checkSpringState(springState, index);
//        for (State state : springState.states) {
//            System.out.print(state.character);
//        }
//        System.out.println(" " + checkResult.valid + " " + index);
        if(!checkResult.valid) {
            return 0;
        }
        if(index >= springState.states.size()) {
            return 1;
        }
        if(springState.states.get(index).equals(State.UNKNOWN)) {
//            if (checkResult.next.equals(State.ON)) {
//                List<State> newStates = new ArrayList<>(springState.states);
//                newStates.set(index, State.ON);
//                return processSpringState(new SpringState(newStates, springState.runs), index + 1);
//            } else if (checkResult.next.equals(State.OFF)) {
//                List<State> newStates = new ArrayList<>(springState.states);
//                newStates.set(index, State.OFF);
//                return processSpringState(new SpringState(newStates, springState.runs), index + 1);
//            } else {
//                List<State> newStates1 = new ArrayList<>(springState.states);
//                newStates1.set(index, State.ON);
//                List<State> newStates2 = new ArrayList<>(springState.states);
//                newStates2.set(index, State.OFF);
//                return processSpringState(new SpringState(newStates1, springState.runs), index + 1) +
//                        processSpringState(new SpringState(newStates2, springState.runs), index + 1);
//            }
            List<State> newStates1 = new ArrayList<>(springState.states);
            newStates1.set(index, State.ON);
            List<State> newStates2 = new ArrayList<>(springState.states);
            newStates2.set(index, State.OFF);
            return processSpringState(new SpringState(newStates1, springState.runs), index + 1) +
                    processSpringState(new SpringState(newStates2, springState.runs), index + 1);
        } else {
            return processSpringState(new SpringState(springState.states, springState.runs), index + 1);
        }
    }

    private static CheckResult checkSpringState(SpringState springState, int index) {
        if(index == 0) {
            return new CheckResult(true);
        }
//        if(index == 6) {
//            int i = 0;
//            ++i;
//        }

        boolean onRun = false;
        int currentRun = 0;
        List<Integer> runs = new ArrayList<>();
        for(int i = 0; i < index; ++i) {
            State state = springState.states.get(i);
            if(state.equals(State.ON)) {
                currentRun++;
                if(!onRun) {
                    onRun = true;
                }
            } else {
                if(onRun) {
                    runs.add(currentRun);
                    onRun = false;
                    currentRun = 0;
                }
            }
        }
        if(onRun) {
            runs.add(currentRun);
        }

        if(index == springState.states.size()) {
            if(springState.runs.size() != runs.size()) {
                return new CheckResult(false);
            }
        }

        for(int i = 0; i < runs.size(); ++i) {
            if(springState.runs.size() <= i) {
                return new CheckResult(false);
            }
            if(i == runs.size() - 1 && onRun && index != springState.states.size()) {
                if(runs.get(i) > springState.runs.get(i)) {
                    return new CheckResult(false);
                }
            } else {
                if(!runs.get(i).equals(springState.runs.get(i))) {
                    return new CheckResult(false);
                }
            }
        }

        return new CheckResult(true);
    }

}
