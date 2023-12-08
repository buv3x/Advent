package lt.mem.advent._2023;

import lt.mem.advent.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2023_7 {

    private static final List<Character> cards = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');

    private static final List<Character> cards2 = Arrays.asList('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

    private static final List<String> combinations = Arrays.asList("11111", "1112", "122", "113", "23", "14", "5");

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/7.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String line : input) {
            Hand hand = new Hand();
            hand.handString = line.substring(0, line.indexOf(" "));
            hand.bet = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
            Map<Character, Integer> combinationMap = new HashMap<>();
            for (char c : hand.handString.toCharArray()) {
                if(!combinationMap.containsKey(c)) {
                    combinationMap.put(c, 0);
                }
                combinationMap.put(c, combinationMap.get(c) + 1);
            }
            String combination = StringUtils.join(combinationMap.values().stream().sorted().collect(Collectors.toList()), "");
            StringBuilder sb = new StringBuilder();
            sb.append(toFootprintChar(combinations.indexOf(combination)));
            for (char c : hand.handString.toCharArray()) {
                sb.append(toFootprintChar(cards.indexOf(c)));
            }

            hand.footprint = sb.toString();
            hands.add(hand);
        }

        hands.sort(Comparator.comparing(h -> h.footprint));

        long total = 0;
        for(int i = 0; i < hands.size(); ++i) {
            total += (long) hands.get(i).bet * (i + 1);
        }

        System.out.println(hands);
        System.out.println(total);

    }


    public static void second(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String line : input) {
            Hand hand = new Hand();
            hand.handString = line.substring(0, line.indexOf(" "));
            hand.bet = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
            Map<Character, Integer> combinationMap = new HashMap<>();
            for (char c : hand.handString.toCharArray()) {
                if(!combinationMap.containsKey(c)) {
                    combinationMap.put(c, 0);
                }
                combinationMap.put(c, combinationMap.get(c) + 1);
            }
            int jokersCount = combinationMap.getOrDefault('J', 0);
            if(jokersCount == 5) {
                jokersCount = 0;
            } else {
                combinationMap.remove('J');
            }

            List<Integer> cardCounts = combinationMap.values().stream().sorted().collect(Collectors.toList());
            cardCounts.set(cardCounts.size() - 1, cardCounts.get(cardCounts.size() - 1) + jokersCount);
            String combination = StringUtils.join(cardCounts, "");
            StringBuilder sb = new StringBuilder();
            sb.append(toFootprintChar(combinations.indexOf(combination)));
            for (char c : hand.handString.toCharArray()) {
                sb.append(toFootprintChar(cards2.indexOf(c)));
            }

            hand.footprint = sb.toString();
            hands.add(hand);
        }

        hands.sort(Comparator.comparing(h -> h.footprint));

        long total = 0;
        for(int i = 0; i < hands.size(); ++i) {
            total += (long) hands.get(i).bet * (i + 1);
        }

        System.out.println(hands);
        System.out.println(total);
    }

    private static char toFootprintChar(int index) {
        return (char) ('a' + index);
    }
    private static class Hand {
        private String handString;

        private int bet;

        private String footprint;

        public String toString() {
            return handString + " -> " + footprint;
        }
    }

}
