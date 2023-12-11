package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class _2023_2 {

    private final static int MAX_RED = 12;
    private final static int MAX_GREEN = 13;
    private final static int MAX_BLUE = 14;

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/2.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        int index = 1;
        int total = 0;
        for (int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            String data = line.substring(line.indexOf(":") + 2);
//            System.out.println(data);
            String[] gameStrings = StringUtils.split(data, ';');
            boolean valid = true;
            for (String gameString : gameStrings) {
                String trimmedGame = gameString.trim();
                String[] colorStrings = StringUtils.split(trimmedGame, ',');
                for (String colorString : colorStrings) {
                    String trimmedColor = colorString.trim();
//                    System.out.println(trimmedColor);
                    int max = 0;
                    if(trimmedColor.contains("red")) {
                        max = MAX_RED;
                    } else if (trimmedColor.contains("blue")) {
                        max = MAX_BLUE;
                    } else if (trimmedColor.contains("green")) {
                        max = MAX_GREEN;
                    }
                    int number = Integer.parseInt(trimmedColor.substring(0, trimmedColor.indexOf(' ')));
                    if(number > max) {
                        valid = false;
                    }
                }
            }
            if(valid) {
                System.out.println(index);
                total += index;
            }
            index++;
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        int total = 0;
        for (int i = 0; i < input.size(); ++i) {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            String line = input.get(i);
            String data = line.substring(line.indexOf(":") + 2);
            String[] gameStrings = StringUtils.split(data, ';');
            boolean valid = true;
            for (String gameString : gameStrings) {
                String trimmedGame = gameString.trim();
                String[] colorStrings = StringUtils.split(trimmedGame, ',');
                for (String colorString : colorStrings) {
                    String trimmedColor = colorString.trim();
                    int number = Integer.parseInt(trimmedColor.substring(0, trimmedColor.indexOf(' ')));
                    if(trimmedColor.contains("red")) {
                        maxRed = Math.max(number, maxRed);
                    } else if (trimmedColor.contains("blue")) {
                        maxBlue = Math.max(number, maxBlue);
                    } else if (trimmedColor.contains("green")) {
                        maxGreen = Math.max(number, maxGreen);
                    }
                }
            }
            int power = maxRed * maxBlue * maxGreen;
            System.out.println(power);
            total += power;
        }
        System.out.println(total);
    }

}
