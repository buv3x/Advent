package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2024_23 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/23.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static Set<String> tNodes = new HashSet<>();

    private static Map<String, Set<String>> connections = new HashMap<>();

    public static void first(List<String> input) {
        fillConnections(input);

        tNodes.addAll(connections.keySet().stream().filter(n -> n.startsWith("t")).collect(Collectors.toSet()));

        Set<String> triplets = new HashSet<>();

        for (String tNode : tNodes) {
            Set<String> secondConnections = connections.get(tNode);
            for (String secondConnection : secondConnections) {
                Set<String> thirdConnections = connections.get(secondConnection);
                for (String thirdConnection : thirdConnections) {
                    if(connections.get(thirdConnection).contains(tNode)) {
                        List<String> triplet = Arrays.asList(tNode, secondConnection, thirdConnection);
                        triplet.sort(Comparator.comparing(x->x));
                        triplets.add(StringUtils.join(triplet, ","));
                    }
                }
            }
        }


        System.out.println(triplets.size());

    }

    private static void fillConnections(List<String> input) {
        for (String row : input) {
            String node1 = ReaderUtil.stringBefore(row, "-");
            String node2 = ReaderUtil.stringAfter(row, "-");

            if(!connections.containsKey(node1)) {
                connections.put(node1, new HashSet<>());
            }

            if(!connections.containsKey(node2)) {
                connections.put(node2, new HashSet<>());
            }

            connections.get(node1).add(node2);
            connections.get(node2).add(node1);
        }
    }

    public static void second(List<String> input) {
        fillConnections(input);

        int size = 3;
        Set<String> cliques = new HashSet<>();
        for (String tNode : connections.keySet()) {
            Set<String> secondConnections = connections.get(tNode);
            for (String secondConnection : secondConnections) {
                Set<String> thirdConnections = connections.get(secondConnection);
                for (String thirdConnection : thirdConnections) {
                    if(connections.get(thirdConnection).contains(tNode)) {
                        List<String> triplet = Arrays.asList(tNode, secondConnection, thirdConnection);
                        triplet.sort(Comparator.comparing(x->x));
                        cliques.add(StringUtils.join(triplet, ","));
                    }
                }
            }
        }

        System.out.println(size + ": " + cliques.size());

        while(cliques.size() > 1) {
            size++;
            Set<String> newCliques = new HashSet<>();
            for (String clique : cliques) {
                Set<String> nodes = new HashSet<>(Arrays.asList(StringUtils.split(clique, ",")));
                for (String newNode : connections.keySet()) {
                    if(!nodes.contains(newNode)) {
                        Set<String> newNodeConnections = connections.get(newNode);
                        if(newNodeConnections.containsAll(nodes)) {
                            List<String> newClique = new ArrayList<>(nodes);
                            newClique.add(newNode);
                            newClique.sort(Comparator.comparing(x->x));
                            newCliques.add(StringUtils.join(newClique, ","));
                        }
                    }
                }
            }
            System.out.println(size + ": " + newCliques.size());
            cliques = newCliques;
        }

        if(cliques.size() == 1) {
            System.out.println(cliques.iterator().next());
        }
    }

}
