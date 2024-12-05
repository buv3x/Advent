package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class _2015_12 {


    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/12.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        char[] input = lineInput.get(0).toCharArray();
        int total = 0;
        boolean run = false;
        StringBuilder runSb = new StringBuilder();
        for (char c : input) {
            if(run) {
                if(c >= '0' && c <= '9') {
                    runSb.append(c);
                } else {
                    total += Integer.parseInt(runSb.toString());
                    run = false;
                    runSb = new StringBuilder();
                }
            } else {
                if((c >= '0' && c <= '9') || c == '-') {
                    runSb.append(c);
                    run = true;
                }
            }
        }

        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        char[] input = lineInput.get(0).toCharArray();
        boolean run = false;
        StringBuilder runSb = new StringBuilder();
        Node root = new Node();
        Node currentNode = root;
        for (char c : input) {
            if(c == '{') {
                Node newNode = new Node();
                newNode.parent = currentNode;
                currentNode.children.add(newNode);
                currentNode = newNode;
                run = false;
                runSb = new StringBuilder();
            } else if (c == '}') {
                if(run) {
                    currentNode.total += Integer.parseInt(runSb.toString());
                }
                run = false;
                runSb = new StringBuilder();
                currentNode = currentNode.parent;
            } else {
                currentNode.content.append(c);
                if(run) {
                    if(c >= '0' && c <= '9') {
                        runSb.append(c);
                    } else {
                        currentNode.total += Integer.parseInt(runSb.toString());
                        run = false;
                        runSb = new StringBuilder();
                    }
                } else {
                    if((c >= '0' && c <= '9') || c == '-') {
                        runSb.append(c);
                        run = true;
                    }
                }
            }
        }

        int x = calculateTotal(root);
        System.out.println(x);

    }

    private static int calculateTotal(Node node) {
        if(node.content.toString().contains(":\"red\"")) {
            return 0;
        } else {
            int total = node.total;
            for (Node child : node.children) {
                total += calculateTotal(child);
            }
            return total;
        }
    }


    private static class Node {
        private Node parent;
        private List<Node> children = new ArrayList<>();
        private int total = 0;
        private StringBuilder content = new StringBuilder();
    }

}
