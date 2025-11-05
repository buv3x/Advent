package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2017_09 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/9.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        String row = lineInput.get(0);
        Node root = null;
        Node currentNode = null;
        boolean garbage = false;
        boolean ignore = false;
//        StringBuilder content = new StringBuilder();
        for (char c : row.toCharArray()) {
            if(!garbage) {
                if(c=='{') {
                    Node node = new Node();
                    node.parent = currentNode;
                    if (currentNode == null) {
                        root = node;
                    } else {
                        currentNode.children.add(node);
                    }
                    currentNode = node;
                } else if(c=='<') {
                    garbage = true;
                } else if(c=='}') {
                    if(currentNode.parent != null) {
                        currentNode = currentNode.parent;
                    }
                }
            } else {
                if(ignore) {
                    ignore = false;
                } else {
                    if(c=='!') {
                        ignore = true;
                    } else if(c=='>') {
                        garbage = false;
                    }
                }
            }
        }

        System.out.println(calculate(root, 1));
    }

    private static int calculate(Node node, int level) {
        int total = level;
        for (Node child : node.children) {
            total += calculate(child, level + 1);
        }
        return total;
    }

    public static void second(List<String> lineInput) {
        String row = lineInput.get(0);
        Node root = null;
        Node currentNode = null;
        boolean garbage = false;
        boolean ignore = false;
//        StringBuilder content = new StringBuilder();
        int total = 0;
        for (char c : row.toCharArray()) {
            if(!garbage) {
                if(c=='{') {
                    Node node = new Node();
                    node.parent = currentNode;
                    if (currentNode == null) {
                        root = node;
                    } else {
                        currentNode.children.add(node);
                    }
                    currentNode = node;
                } else if(c=='<') {
                    garbage = true;
                } else if(c=='}') {
                    if(currentNode.parent != null) {
                        currentNode = currentNode.parent;
                    }
                }
            } else {
                if(ignore) {
                    ignore = false;
                } else {
                    if(c=='!') {
                        ignore = true;
                    } else if(c=='>') {
                        garbage = false;
                    } else {
                        total++;
                    }
                }
            }
        }
        System.out.println(total);
    }

    private static class Node {
        String content;
        Node parent;
        List<Node> children = new ArrayList<>();
    }

}
