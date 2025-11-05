package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2017_07 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/7.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        Set<String> nodes = new HashSet<>();
        Set<String> children = new HashSet<>();
        for (String row : lineInput) {
            nodes.add(ReaderUtil.stringBefore(row, " ("));
            if(row.contains("->")) {
                children.addAll(Arrays.asList(StringUtils.split(ReaderUtil.stringAfter(row, "-> "), ", ")));
            }
        }
        System.out.println(nodes.size());
        System.out.println(children.size());

        for (String node : nodes) {
            if(!children.contains(node)) {
                System.out.println(node);
                return;
            }
        }

    }

    private static final Map<String, Node> nodes = new HashMap<>();

    public static void second(List<String> lineInput) {
        for (String row : lineInput) {
            Node node = new Node();
            String key = ReaderUtil.stringBefore(row, " (");
            node.key = key;
            node.weight = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, ")"), " ("));
            if(row.contains("->")) {
                node.childrenKeys = Arrays.asList(StringUtils.split(ReaderUtil.stringAfter(row, "-> "), ", "));
            }
            nodes.put(key, node);
        }

        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            Node node = entry.getValue();
            if(CollectionUtils.isNotEmpty(node.childrenKeys)) {
                node.children = new ArrayList<>();
                for (String childrenKey : node.childrenKeys) {
                    Node child = nodes.get(childrenKey);
                    node.children.add(child);
                    child.parent = node;
                }
            }
        }

        Node root = null;
        for (Node node : nodes.values()) {
            if(node.parent == null) {
                System.out.println(node.key);
                root = node;
            }
        }

        calculate(root);
        System.out.println(root.totalWeight);
        balance(root);


    }

    private static void balance(Node node) {
        if(CollectionUtils.isNotEmpty(node.children)) {
            List<Pair<Integer, String>> sums = new ArrayList<>();
            for (Node child : node.children) {
                sums.add(Pair.of(child.totalWeight, child.key));
                balance(child);
            }
            Map<Integer, Integer> counter = new HashMap<>();
            for (Pair<Integer, String> sum : sums) {
                counter.merge(sum.getKey(), 1, Integer::sum);
            }
            if(counter.size() > 1) {
                System.out.println(node.key + " " + sums);
                int problem = -1;
                int unproblem = -1;
                for (Map.Entry<Integer, Integer> appearances : counter.entrySet()) {
                    if(appearances.getValue().equals(1)) {
                        problem = appearances.getKey();
                    } else {
                        unproblem = appearances.getKey();
                    }
                }

                for (Node child : node.children) {
                    if(child.totalWeight == problem) {
                        System.out.println(child.key);
                    }
                    System.out.println(child.weight + unproblem - problem);
                }
            }
        }
    }

    private static void calculate(Node node) {
        int total = node.weight;
        if(CollectionUtils.isNotEmpty(node.children)) {
            for (Node child : node.children) {
                calculate(child);
                total += child.totalWeight;
            }
        }
        node.totalWeight = total;
    }

    private static class Node {

        String key;
        Node parent = null;
        List<Node> children;
        List<String> childrenKeys;
        int weight;
        int totalWeight = -1;

    }

}
