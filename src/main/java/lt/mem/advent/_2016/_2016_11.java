package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2016_11 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/11.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<String, Integer> materialsMap = new HashMap<>();

    public static void first(List<String> lineInput) {
        State initialState = initialize(lineInput);
        process(initialState);
    }

    private static void process(State initialState) {
        List<State> toProcess = new ArrayList<>();
        toProcess.add(initialState);
        Set<String> processedStates = new HashSet<>();
        int steps = 0;
        outer: while (true) {
            System.out.println("Step " + steps + ". Size " + toProcess.size());
            for (State state : toProcess) {
                if(state.isFinal()) {
                    System.out.println(state.leadingStates);
                    break outer;
                }
            }
            List<State> newToProcess = new ArrayList<>();
            for (State state : toProcess) {
                if(processedStates.contains(state.createKey())) {
                    continue;
                }
                boolean isValid = isValid(state);
                if(!isValid) {
                    continue;
                }

                List<Element> elements = state.floorElements.get(state.elevator - 1);
                List<Integer> newElevators = new ArrayList<>();
                if(state.elevator == 1) {
                    newElevators.add(2);
                } else if(state.elevator == 2) {
                    newElevators.add(1);
                    newElevators.add(3);
                } else if(state.elevator == 3) {
                    newElevators.add(2);
                    newElevators.add(4);
                } else {
                    newElevators.add(3);
                }
                for (Element firstElement : elements) {
                    for (Element secondElement : elements) {
//                        if(firstElement.chip && !secondElement.chip && !firstElement.name.equals(secondElement.name)) {
//                            continue;
//                        }
//                        if(secondElement.chip && !firstElement.chip && !firstElement.name.equals(secondElement.name)) {
//                            continue;
//                        }
                        for (Integer newElevator : newElevators) {
                            State newState = new State();
                            newState.elevator = newElevator;
                            List<List<Element>> newFloorElements = new ArrayList<>();
                            for(int i = 0; i < 4; ++i) {
                                List<Element> stateElements = state.floorElements.get(i);
                                List<Element> floorElements = new ArrayList<>();
                                if(state.elevator - 1 == i) {
                                    for (Element element : stateElements) {
                                        if(!element.equals(firstElement) && !element.equals(secondElement)) {
                                            floorElements.add(element);
                                        }
                                    }
                                } else {
                                    floorElements.addAll(stateElements);
                                    if(newElevator - 1 == i) {
                                        floorElements.add(firstElement);
                                        if(!secondElement.equals(firstElement)) {
                                            floorElements.add(secondElement);
                                        }
                                    }
                                }
                                newFloorElements.add(floorElements);
                            }
                            newState.floorElements = newFloorElements;
                            newState.leadingStates.addAll(state.leadingStates);
                            newState.leadingStates.add(state.createKey());
                            newToProcess.add(newState);
                        }
                    }
                }
                processedStates.add(state.createKey());
            }
            toProcess = newToProcess;
            steps++;
        }
        System.out.println(steps);
    }

    private static State initialize(List<String> lineInput) {
        State initialState = new State();
        initialState.elevator = 1;
        for (int i = 0; i < lineInput.size() - 1; ++i) {
            String row = lineInput.get(i);
            String contains = StringUtils.strip(ReaderUtil.stringAfter(row, "contains "), ".");
            String[] splitted = StringUtils.splitByWholeSeparator(contains, ", ");
            List<String> splittedList = new ArrayList<>();
            for (String split : splitted) {
                if(split.contains(" and ")) {
                    splittedList.add(ReaderUtil.stringBefore(split, " and "));
                    splittedList.add(ReaderUtil.stringAfter(split, " and "));
                } else {
                    splittedList.add(split);
                }
            }

            List<Element> floorElements = new ArrayList<>();
            for (String elementString : splittedList) {
                Element element = new Element();
                if(elementString.contains("-compatible")) {
                    element.chip = true;
                    element.name = ReaderUtil.stringBefore(ReaderUtil.stringAfter(elementString, "a "), "-compatible");
                } else {
                    element.chip = false;
                    element.name = ReaderUtil.stringBefore(ReaderUtil.stringAfter(elementString, "a "), " generator");
                }
                if(!materialsMap.containsKey(element.name)) {
                    materialsMap.put(element.name, materialsMap.size());
                }
                floorElements.add(element);
            }
            initialState.floorElements.add(floorElements);
        }
        initialState.floorElements.add(new ArrayList<>());
        return initialState;
    }

    private static boolean isValid(State state) {
        for (List<Element> elements : state.floorElements) {
            for (Element chip : elements) {
                if(!chip.chip) {
                    continue;
                }
                boolean hasOwnGenerator = false;
                boolean hasOtherGenerator = false;
                for (Element element : elements) {
                    if(!element.chip) {
                        if(element.name.equals(chip.name)) {
                            hasOwnGenerator = true;
                        } else {
                            hasOtherGenerator = true;
                        }
                    }
                }
                if(hasOtherGenerator && !hasOwnGenerator) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void second(List<String> lineInput) {
        State initialState = initialize(lineInput);
        initialState.step = 0;
        materialsMap.put("elerium", materialsMap.size());
        materialsMap.put("dilithium", materialsMap.size());

        Element element = new Element();
        element.chip = true;
        element.name = "dilithium";
        initialState.floorElements.get(0).add(element);

        element = new Element();
        element.chip = false;
        element.name = "dilithium";
        initialState.floorElements.get(0).add(element);

        element = new Element();
        element.chip = true;
        element.name = "elerium";
        initialState.floorElements.get(0).add(element);

        element = new Element();
        element.chip = false;
        element.name = "elerium";
        initialState.floorElements.get(0).add(element);

        process2(initialState);
    }

    private static void process2(State initialState) {
        List<State> toProcess = new ArrayList<>();
        toProcess.add(initialState);
        Set<String> processedStates = new HashSet<>();
        int steps = 0;
        outer: while (true) {
            System.out.println("Step " + steps + ". Size " + toProcess.size());
            for (State state : toProcess) {
                if(state.isFinal()) {
                    System.out.println(state.leadingStates);
                    System.out.println(state.step);
                    break outer;
                }
            }
            List<State> newToProcess = new ArrayList<>();
            for (State state : toProcess) {
                if(processedStates.contains(state.createKey())) {
                    continue;
                }
                boolean isValid = isValid(state);
                if(!isValid) {
                    continue;
                }

                List<Element> elements = state.floorElements.get(state.elevator - 1);
                List<Integer> newElevators = new ArrayList<>();
                if(state.elevator == 1) {
                    newElevators.add(2);
                } else if(state.elevator == 2) {
                    newElevators.add(1);
                    newElevators.add(3);
                } else if(state.elevator == 3) {
                    newElevators.add(2);
                    newElevators.add(4);
                } else {
                    newElevators.add(3);
                }
                for (Element firstElement : elements) {
                    for (Element secondElement : elements) {
//                        if(firstElement.chip && !secondElement.chip && !firstElement.name.equals(secondElement.name)) {
//                            continue;
//                        }
//                        if(secondElement.chip && !firstElement.chip && !firstElement.name.equals(secondElement.name)) {
//                            continue;
//                        }
                        for (Integer newElevator : newElevators) {
                            State newState = new State();
                            newState.elevator = newElevator;
                            List<List<Element>> newFloorElements = new ArrayList<>();
                            for(int i = 0; i < 4; ++i) {
                                List<Element> stateElements = state.floorElements.get(i);
                                List<Element> floorElements = new ArrayList<>();
                                if(state.elevator - 1 == i) {
                                    for (Element element : stateElements) {
                                        if(!element.equals(firstElement) && !element.equals(secondElement)) {
                                            floorElements.add(element);
                                        }
                                    }
                                } else {
                                    floorElements.addAll(stateElements);
                                    if(newElevator - 1 == i) {
                                        floorElements.add(firstElement);
                                        if(!secondElement.equals(firstElement)) {
                                            floorElements.add(secondElement);
                                        }
                                    }
                                }
                                newFloorElements.add(floorElements);
                            }
                            newState.floorElements = newFloorElements;
                            newState.leadingStates.addAll(state.leadingStates);
                            newState.leadingStates.add(state.createKey());
                            newState.step = state.step + 1;
                            newToProcess.add(newState);
                        }
                    }
                }
                processedStates.add(state.createKey());
            }
            toProcess = newToProcess;
            steps++;
        }
//        System.out.println(steps);
    }

    private static class State  {

        int step;
        int elevator;
        List<List<Element>> floorElements = new ArrayList<>();
        List<String> leadingStates = new ArrayList<>();

        boolean isFinal() {
            return floorElements.get(0).size() == 0 && floorElements.get(1).size() == 0 && floorElements.get(2).size() == 0;
        }

        String createKey() {
            StringBuilder sb = new StringBuilder();
            sb.append(elevator);
            sb.append("_");
            Map<String, Integer> tempMaterialsMap = new HashMap<>();
            for (List<Element> floorElement : floorElements) {
                List<Integer> chips = new ArrayList<>();
                List<Integer> generators = new ArrayList<>();
                for (Element element : floorElement) {
                    if(element.chip) {
                        if(!tempMaterialsMap.containsKey(element.name)) {
                            tempMaterialsMap.put(element.name, tempMaterialsMap.size());
                        }
                        chips.add(tempMaterialsMap.get(element.name));
                    }
                }
                for (Element element : floorElement) {
                    if(!element.chip) {
                        if(!tempMaterialsMap.containsKey(element.name)) {
                            tempMaterialsMap.put(element.name, tempMaterialsMap.size());
                        }
                        generators.add(tempMaterialsMap.get(element.name));
                    }
                }
                Collections.sort(chips);
                Collections.sort(generators);
                for (Integer chip : chips) {
                    sb.append(chip);
                }
                sb.append("|");
                for (Integer generator : generators) {
                    sb.append(generator);
                }
                sb.append("_");
            }
            return sb.toString();
        }
    }

    private static class Element {
        boolean chip;
        String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Element)) return false;
            Element element = (Element) o;
            return chip == element.chip && Objects.equals(name, element.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(chip, name);
        }
    }


}
