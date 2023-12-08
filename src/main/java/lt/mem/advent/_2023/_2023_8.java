package lt.mem.advent._2023;

import lt.mem.advent.ReaderUtil;

import java.util.*;

public class _2023_8 {

    private static Map<String, Node> map = new HashMap<>();

    private static Map<State, Destination> path = new HashMap<>();

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/8.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        String steps = parseInput(input);

        int index = 0;
        String currentNode = "AAA";
        System.out.println(currentNode);
        while (!currentNode.equals("ZZZ")) {
            char step = steps.charAt(index % steps.length());
            currentNode = step == 'L' ? map.get(currentNode).left : map.get(currentNode).right;
            System.out.println(currentNode);
            index++;
        }
        System.out.println(index);
    }

    public static void second(List<String> input) {
        String steps = parseInput(input);
        int stepsLength = steps.length();
        System.out.println(stepsLength);

        List<State> currentStates = new ArrayList<>();
        for (String key : map.keySet()) {
            if(key.endsWith("A")) {
                State state = new State();
                currentStates.add(state);
                Node currentNode = map.get(key);
                int index = 0;
                while (currentNode.name.charAt(currentNode.name.length() - 1) != 'Z') {
                    char step = steps.charAt(index % stepsLength);
                    currentNode = step == 'L' ? currentNode.leftNode : currentNode.rightNode;
                    index++;
                }
                state.node = currentNode;
                state.index = index % stepsLength;
                state.total = index;
            }
        }

        System.out.println(currentStates);

        while(!areEqual(currentStates)) {
            State minState = currentStates.stream().min(Comparator.comparing(s -> s.total)).get();
            if(path.containsKey(minState)) {
                Destination destination = path.get(minState);
                minState.node = destination.node;
                minState.index = (destination.steps + minState.index) % stepsLength;
                minState.total += destination.steps;
            } else {
                Node currentNode = minState.node;
                int index = 0;
                while (currentNode.name.charAt(currentNode.name.length() - 1) != 'Z' || index == minState.index) {
                    char step = steps.charAt((index + minState.index) % stepsLength);
                    currentNode = step == 'L' ? currentNode.leftNode : currentNode.rightNode;
                    index++;
                }
                path.put(new State(currentNode, minState.index), new Destination(currentNode, index));
                minState.node = currentNode;
                minState.index = (index + minState.index) % stepsLength;
                minState.total += index;
            }

//            System.out.println(currentStates);
        }
        System.out.println(currentStates);

//        System.out.println(currentNodes);
//        while (!isFinal(currentNodes)) {
//            char step = steps.charAt((int) (index % steps.length()));
//            List<Node> newCurrentNodes = new ArrayList<>();
//            for (Node currentNode : currentNodes) {
//                newCurrentNodes.add(step == 'L' ? currentNode.leftNode : currentNode.rightNode);
//            }
//            currentNodes = newCurrentNodes;
////            System.out.println(currentNodes);
//            index++;
//            if(index % 100000000 == 0) {
//                System.out.println(index);
//            }
//        }
//        System.out.println(index);
    }

    private static boolean isFinal(List<Node> nodes) {
        for (Node node : nodes) {
            if(node.name.charAt(node.name.length() - 1) != 'Z') {
                return false;
            }
        }
        return true;
    }

    private static boolean areEqual(List<State> states) {
        long total = -1;
        for (State state : states) {
            if(total == -1) {
                total = state.total;
            } else {
                if(total != state.total) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String parseInput(List<String> input) {
        String steps = input.get(0);
        for(int i = 2; i < input.size(); ++i) {
            String nodeString = input.get(i);
            String root = ReaderUtil.stringBefore(nodeString, " = ");
            String left = ReaderUtil.stringBefore(ReaderUtil.stringAfter(nodeString, "("), ",");
            String right = ReaderUtil.stringBefore(ReaderUtil.stringAfter(nodeString, ", "), ")");
            map.put(root, new Node(root, left, right));
        }

        for (Node node : map.values()) {
            node.leftNode = map.get(node.left);
            node.rightNode = map.get(node.right);
        }

        return steps;
    }

    private static class Node {
        String name;
        String left;
        String right;
        Node leftNode;
        Node rightNode;

        public Node(String name, String left, String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }

    private static class Destination {
        Node node;
        int steps;

        public Destination(Node node, int steps) {
            this.node = node;
            this.steps = steps;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Destination that = (Destination) o;
            return steps == that.steps && Objects.equals(node, that.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, steps);
        }
    }

    private static class State {
        Node node;
        int index;
        long total;

        public String toString() {
            return node.name + " " + total + " " + index;
        }

        State(Node node, long total) {
            this.node = node;
            this.total = total;
        }

        State() {}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return index == state.index && Objects.equals(node, state.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node.name, index);
        }
    }

}
