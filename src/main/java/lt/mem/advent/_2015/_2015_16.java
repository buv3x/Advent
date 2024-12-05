package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2015_16 {

    private static final Map<String, Integer> CLUES = Map.ofEntries(
            Map.entry("children", 3),
            Map.entry("cats", 7),
            Map.entry("samoyeds", 2),
            Map.entry("pomeranians", 3),
            Map.entry("akitas", 0),
            Map.entry("vizslas", 0),
            Map.entry("goldfish", 5),
            Map.entry("trees", 3),
            Map.entry("cars", 2),
            Map.entry("perfumes", 1));

    private static final List<Map<String, Integer>> aunts = new ArrayList<>();

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/16.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        fillAunts(lineInput);

        Set<Integer> validAunts = new HashSet<>();
        for(int i = 0; i < aunts.size(); ++i) {
            validAunts.add(i);
        }

        process(validAunts, true);

        System.out.println(validAunts);
        if(validAunts.size() == 1) {
            System.out.println(validAunts.iterator().next() + 1);
        }

    }

    private static void process(Set<Integer> validAunts, boolean exact) {
        for (Map.Entry<String, Integer> clue : CLUES.entrySet()) {
            for(int i = 0; i < aunts.size(); ++i) {
                if(!validAunts.contains(i)) {
                    continue;
                }
                Map<String, Integer> aunt = aunts.get(i);
                if(aunt.containsKey(clue.getKey())) {
                    if(exact) {
                        if (!aunt.get(clue.getKey()).equals(clue.getValue())) {
                            validAunts.remove(i);
                        }
                    } else {
                        if(clue.getKey().equals("cats") || clue.getKey().equals("trees")) {
                            if (aunt.get(clue.getKey()) <= clue.getValue()) {
                                validAunts.remove(i);
                            }
                        } else if(clue.getKey().equals("pomeranians") || clue.getKey().equals("goldfish")) {
                            if (aunt.get(clue.getKey()) >= clue.getValue()) {
                                validAunts.remove(i);
                            }
                        } else {
                            if (!aunt.get(clue.getKey()).equals(clue.getValue())) {
                                validAunts.remove(i);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void fillAunts(List<String> lineInput) {
        for (String line : lineInput) {
            Map<String, Integer> aunt = new HashMap<>();
            String values = ReaderUtil.stringAfter(line, ": ");
            while(true) {
                String name = ReaderUtil.stringBefore(values, ": ");
                String valueLine = ReaderUtil.stringAfter(values, ": ");
                int value;
                if(valueLine.contains(", ")) {
                    value = Integer.parseInt(ReaderUtil.stringBefore(valueLine, ", "));
                } else {
                    value = Integer.parseInt(valueLine);
                }
                aunt.put(name, value);
                if(!values.contains(", ")) {
                    break;
                }
                values = ReaderUtil.stringAfter(values, ", ");
            }
            aunts.add(aunt);
        }
    }


    public static void second(List<String> lineInput) {

        fillAunts(lineInput);

        Set<Integer> validAunts = new HashSet<>();
        for(int i = 0; i < aunts.size(); ++i) {
            validAunts.add(i);
        }

        process(validAunts, false);

        System.out.println(validAunts);
        if(validAunts.size() == 1) {
            System.out.println(validAunts.iterator().next() + 1);
        }


    }


}
