package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_20 {

    private final static Map<String, Node> nodesMap = new HashMap<>();

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/20.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        for (String line : input) {
            String inputString = ReaderUtil.stringBefore(line, " -> ");
            Node node;
            if(inputString.startsWith("%")) {
                node = new FlipNode();
                node.name = inputString.substring(1);
            } else if(inputString.startsWith("&")) {
                node = new ConjunctionNode();
                node.name = inputString.substring(1);
            } else {
                node = new InitialNode();
                node.name = inputString;
            }
            nodesMap.put(node.name, node);
            node.output.addAll(Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(line, " -> "), ", ")).collect(Collectors.toList()));
        }

        for (Node value : nodesMap.values()) {
            for (String out : value.output) {
                if (nodesMap.containsKey(out)) {
                    Node outNode = nodesMap.get(out);
                    if(outNode instanceof ConjunctionNode) {
                       ConjunctionNode conjunctionNode = (ConjunctionNode) outNode;
                       conjunctionNode.inputs.put(value.name, false);
                    }
                }
            }
        }

        long highTotal = 0;
        long lowTotal = 0;
        for(int i = 0; i < 1000; ++i) {
            List<Pulse> pulses = Arrays.asList(new Pulse(null, "broadcaster", false));
            while (CollectionUtils.isNotEmpty(pulses)) {
                List<Pulse> newPulses = new ArrayList<>();
                for (Pulse pulse : pulses) {
                    if (!nodesMap.containsKey(pulse.to)) {
                        TestNode node = new TestNode();
                        node.name = pulse.to;
                        nodesMap.put(pulse.to, node);
                    }

                    if (pulse.high) {
                        ++highTotal;
                    } else {
                        ++lowTotal;
                    }
//                    System.out.println(pulse);

                    newPulses.addAll(nodesMap.get(pulse.to).processPulse(pulse));
                }
                pulses = newPulses;
            }
        }
        System.out.println(lowTotal);
        System.out.println(highTotal);
        System.out.println(lowTotal * highTotal);
    }

    public static void second(List<String> input) {
        for (String line : input) {
            String inputString = ReaderUtil.stringBefore(line, " -> ");
            Node node;
            if(inputString.startsWith("%")) {
                node = new FlipNode();
                node.name = inputString.substring(1);
            } else if(inputString.startsWith("&")) {
                node = new ConjunctionNode();
                node.name = inputString.substring(1);
            } else {
                node = new InitialNode();
                node.name = inputString;
            }
            nodesMap.put(node.name, node);
            node.output.addAll(Arrays.stream(StringUtils.split(ReaderUtil.stringAfter(line, " -> "), ", ")).collect(Collectors.toList()));
        }

        for (Node value : nodesMap.values()) {
            for (String out : value.output) {
                if (nodesMap.containsKey(out)) {
                    Node outNode = nodesMap.get(out);
                    if(outNode instanceof ConjunctionNode) {
                        ConjunctionNode conjunctionNode = (ConjunctionNode) outNode;
                        conjunctionNode.inputs.put(value.name, false);
                    }
                }
            }
        }

        List<String> finalNodes = new ArrayList<>();
        for (Node node : nodesMap.values()) {
            if(node.output.contains("zh")) {
                finalNodes.add(node.name);
            }
        }
        System.out.println(finalNodes);

        int index = 1;
        outerloop:
        while(index < 1000000) {
            List<Pulse> pulses = Arrays.asList(new Pulse(null, "broadcaster", false));
            while (CollectionUtils.isNotEmpty(pulses)) {
                List<Pulse> newPulses = new ArrayList<>();
                for (Pulse pulse : pulses) {
                    if(finalNodes.contains(pulse.from) && pulse.high) {
                        System.out.println(pulse.from + ": " + index);
                    }
//                    if(pulse.to.equals("rx")) {
//                        System.out.println(index + " " + pulse.high);
//                    }
                    if(pulse.to.equals("rx") && !pulse.high) {
                        break outerloop;
                    }
                    if (!nodesMap.containsKey(pulse.to)) {
                        TestNode node = new TestNode();
                        node.name = pulse.to;
                        nodesMap.put(pulse.to, node);
                    }

                    newPulses.addAll(nodesMap.get(pulse.to).processPulse(pulse));
                }
                pulses = newPulses;
            }
//            if(index % 100000 == 0) {
//                System.out.println(index);
//            }
            ++index;
        }
        System.out.println(index);
    }

    private static abstract class Node {
        List<String> output = new ArrayList<>();
        String name;
        abstract List<Pulse> processPulse(Pulse pulse);
    }

    private static class InitialNode extends Node {
        @Override
        public List<Pulse> processPulse(Pulse pulse) {
            List<Pulse> pulses = new ArrayList<>();
            for (String out : output) {
                pulses.add(new Pulse(name, out, pulse.high));
            }
            return pulses;
        }
    }

    private static class TestNode extends Node {
        @Override
        public List<Pulse> processPulse(Pulse pulse) {
            return Collections.emptyList();
        }
    }

    private static class FlipNode extends Node {
        boolean high = true;
        @Override
        public List<Pulse> processPulse(Pulse pulse) {
            if(pulse.high) {
                return Collections.emptyList();
            }
            List<Pulse> pulses = new ArrayList<>();
            for (String out : output) {
                pulses.add(new Pulse(name, out, high));
            }
            high = !high;
            return pulses;
        }
    }

    private static class ConjunctionNode extends Node {
        Map<String, Boolean> inputs = new HashMap<>();
        @Override
        public List<Pulse> processPulse(Pulse pulse) {
            inputs.put(pulse.from, pulse.high);
            boolean allHigh = inputs.values().stream().allMatch(p -> p);
            List<Pulse> pulses = new ArrayList<>();
            for (String out : output) {
                pulses.add(new Pulse(name, out, !allHigh));
            }
            return pulses;
        }
    }

    private static class Pulse {
        String from;
        String to;
        boolean high;

        public Pulse(String from, String to, boolean high) {
            this.from = from;
            this.to = to;
            this.high = high;
        }

        @Override
        public String toString() {
            return from +
                    " -> " + to + " " + (high ? "^" : "v");
        }
    }

}
