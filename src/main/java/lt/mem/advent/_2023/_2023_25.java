package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2023_25 {

    private static final Map<String, Set<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/25.txt");
        first(input);
//        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        for (String line : input) {
            String startNode = ReaderUtil.stringBefore(line, ":");
            String[] endNodes = StringUtils.split(ReaderUtil.stringAfter(line, ": "), " ");
            for (String endNode : endNodes) {
                if(!graph.containsKey(startNode)) {
                    graph.put(startNode, new HashSet<>());
                }
                graph.get(startNode).add(endNode);
                if(!graph.containsKey(endNode)) {
                    graph.put(endNode, new HashSet<>());
                }
                graph.get(endNode).add(startNode);
            }
        }

        String initialNode = graph.keySet().iterator().next();
        System.out.println(initialNode);
        outerloop:
        for (String destinationNode : graph.keySet()) {
            if(destinationNode.equals(initialNode)) {
                continue;
            }
            List<String> path = findPath(initialNode, destinationNode, new HashSet<>());
            for(int i = 0; i < path.size() - 1; ++i) {
                Edge edge1 = new Edge(path.get(i), path.get(i + 1));
                Set<Edge> restrictedEdges1 = new HashSet<>();
                restrictedEdges1.add(edge1);
                List<String> path1 = findPath(initialNode, destinationNode, restrictedEdges1);
                for(int j = 0; j < path1.size() - 1; ++j) {
                    Edge edge2 = new Edge(path1.get(j), path1.get(j + 1));
                    Set<Edge> restrictedEdges2 = new HashSet<>();
                    restrictedEdges2.add(edge1);
                    restrictedEdges2.add(edge2);
                    List<String> path2 = findPath(initialNode, destinationNode, restrictedEdges2);
                    for(int k = 0; k < path2.size() - 1; ++k) {
                        Edge edge3 = new Edge(path2.get(k), path2.get(k + 1));
                        Set<Edge> restrictedEdges3 = new HashSet<>();
                        restrictedEdges3.add(edge1);
                        restrictedEdges3.add(edge2);
                        restrictedEdges3.add(edge3);
                        List<String> path3 = findPath(initialNode, destinationNode, restrictedEdges3);
                        if(CollectionUtils.isEmpty(path3)) {
                            System.out.println(initialNode + "-" + destinationNode + " different areas");
                            System.out.println(restrictedEdges3);
                            int area = findArea(initialNode, restrictedEdges3);
                            System.out.println(area);
                            System.out.println(graph.size() - area);
                            System.out.println(area * (graph.size() - area));
                            break outerloop;
                        }
                    }
                }
            }
            System.out.println(initialNode + "-" + destinationNode + " same areas");
        }
    }

    private static List<String> findPath(String initialNode, String destinationNode, Set<Edge> restrictedEdges) {
        Map<String, List<String>> pathMap = new HashMap<>();
        pathMap.put(initialNode, new ArrayList<>());
        pathMap.get(initialNode).add(initialNode);

        Set<String> toProcess = new HashSet<>();
        toProcess.add(initialNode);
        while(CollectionUtils.isNotEmpty(toProcess)) {
            Set<String> newNodes = new HashSet<>();
            for (String processNode : toProcess) {
                for (String nextNode : graph.get(processNode)) {
                    if(restrictedEdges.contains(new Edge(processNode, nextNode))) {
                        continue;
                    }

                    if(!pathMap.containsKey(nextNode)) {
                        List<String> newPath = new ArrayList<>(pathMap.get(processNode));
                        newPath.add(nextNode);
                        if(nextNode.equals(destinationNode)) {
                            return newPath;
                        }
                        pathMap.put(nextNode, newPath);
                        newNodes.add(nextNode);
                    }
                }
            }
            toProcess = newNodes;
        }

        return Collections.emptyList();
    }

    private static int findArea(String initialNode, Set<Edge> restrictedEdges) {
        Set<String> pathSet = new HashSet<>();
        pathSet.add(initialNode);

        Set<String> toProcess = new HashSet<>();
        toProcess.add(initialNode);
        while(CollectionUtils.isNotEmpty(toProcess)) {
            Set<String> newNodes = new HashSet<>();
            for (String processNode : toProcess) {
                for (String nextNode : graph.get(processNode)) {
                    if(restrictedEdges.contains(new Edge(processNode, nextNode))) {
                        continue;
                    }

                    if(!pathSet.contains(nextNode)) {
                        pathSet.add(nextNode);
                        newNodes.add(nextNode);
                    }
                }
            }
            toProcess = newNodes;
        }

        return pathSet.size();
    }

    public static void second(List<String> input) {

    }

    private static class Edge {
        String firstNode;
        String secondNode;

        public Edge(String firstNode, String secondNode) {
            if(firstNode.compareTo(secondNode) > 0) {
                this.firstNode = firstNode;
                this.secondNode = secondNode;
            } else {
                this.firstNode = secondNode;
                this.secondNode = firstNode;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equals(firstNode, edge.firstNode) && Objects.equals(secondNode, edge.secondNode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstNode, secondNode);
        }

        @Override
        public String toString() {
            return firstNode + '-' + secondNode;
        }
    }
}
