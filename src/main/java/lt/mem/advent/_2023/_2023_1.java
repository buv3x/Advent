package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class _2023_1 {

    private static Map<String, Integer> map = Map.ofEntries(
            Map.entry("1", 1),
            Map.entry("2", 2),
            Map.entry("3", 3),
            Map.entry("4", 4),
            Map.entry("5", 5),
            Map.entry("6", 6),
            Map.entry("7", 7),
            Map.entry("8", 8),
            Map.entry("9", 9),
            Map.entry("one", 1),
            Map.entry("two", 2),
            Map.entry("three", 3),
            Map.entry("four", 4),
            Map.entry("five", 5),
            Map.entry("six", 6),
            Map.entry("seven", 7),
            Map.entry("eight", 8),
            Map.entry("nine", 9)
    );
    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/1.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        int total = 0;
        for (String line : input) {
            Character firstDigit = line.chars().mapToObj(i -> (char) i).filter(Character::isDigit).findFirst().get();
            Character lastDigit = new StringBuilder(line).reverse().toString().chars()
                    .mapToObj(i -> (char) i).filter(Character::isDigit).findFirst().get();
            total += Integer.parseInt(firstDigit + "" + lastDigit);
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        int total = 0;
        for (String line : input) {
            Integer min = map.get(map.keySet().stream().min(Comparator.comparingInt(o -> getFirstIndexCheck(line.indexOf(o)))).get());
            Integer max = map.get(map.keySet().stream().max(Comparator.comparingInt(o -> getLastIndexCheck(line.lastIndexOf(o)))).get());
            System.out.println(min + "" + max);
            total += Integer.parseInt(min + "" + max);
        }
        System.out.println(total);
    }

    private static int getFirstIndexCheck(int index) {
        return index != -1 ? index : Integer.MAX_VALUE;
    }

    private static int getLastIndexCheck(int index) {
        return index != -1 ? index : Integer.MIN_VALUE;
    }
}
