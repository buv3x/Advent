package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class _2016_10 {

    public static final int OFFSET = 10000;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/10.txt");
//        first(lineInput);
        second(lineInput);
    }

    private static Map<Integer, Bot> bots = new HashMap<>();

    private static Map<Integer, Integer> outputs = new HashMap<>();

    private static Set<Integer> comparisonCheck = new TreeSet<>(Set.of(17, 61));

    public static void first(List<String> lineInput) {
        parseBots(lineInput);

        outer: while(true) {
            for (Bot bot : bots.values()) {
                if(CollectionUtils.isEqualCollection(bot.hold, comparisonCheck)) {
                    System.out.println(bot.botIndex);
                    return;
                }
            }
            for (Bot bot : bots.values()) {
                if(bot.hold.size() > 2) {
                    System.out.println(bot.botIndex + " Error! Error!");
                }
                if(bot.hold.size() == 2) {
                    Iterator<Integer> iterator = bot.hold.iterator();
                    int low = iterator.next();
                    int high = iterator.next();

                    if(bot.lowIndex < OFFSET) {
                        bots.get(bot.lowIndex).hold.add(low);
                    }
                    if(bot.highIndex < OFFSET) {
                        bots.get(bot.highIndex).hold.add(high);
                    }

                    bot.hold = new TreeSet<>();
                    continue outer;
                }
            }
        }

    }

    private static void parseBots(List<String> lineInput) {
        for (String row : lineInput) {
            if(row.startsWith("value ")) {
                int value = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " goes to"), "value "));
                int botIndex = Integer.parseInt(ReaderUtil.stringAfter(row, "to bot "));

                if(!bots.containsKey(botIndex)) {
                    bots.put(botIndex, new Bot());
                }
                Bot bot = bots.get(botIndex);
                bot.hold.add(value);
                bot.botIndex = botIndex;
            } else {
                int botIndex = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " gives "), "bot "));
                String lowOutput = ReaderUtil.stringAfter(ReaderUtil.stringBefore(row, " and high "), "low to ");
                String highOutput = ReaderUtil.stringAfter(row, " high to ");
                int lowIndex = Integer.parseInt(ReaderUtil.stringAfter(lowOutput, " "));
                if(lowOutput.contains("output")) {
                    lowIndex += OFFSET;
                }
                int highIndex = Integer.parseInt(ReaderUtil.stringAfter(highOutput, " "));
                if(highOutput.contains("output")) {
                    highIndex += OFFSET;
                }

                if(!bots.containsKey(botIndex)) {
                    bots.put(botIndex, new Bot());
                }
                Bot bot = bots.get(botIndex);
                bot.lowIndex = lowIndex;
                bot.highIndex = highIndex;
                bot.botIndex = botIndex;
            }
        }
    }

    public static void second(List<String> lineInput) {
        parseBots(lineInput);

        outer: while(true) {
            if(outputs.containsKey(0) && outputs.containsKey(1) && outputs.containsKey(2)) {
                System.out.println(outputs.get(0) * outputs.get(1) * outputs.get(2));
                return;
            }
            for (Bot bot : bots.values()) {
                if(bot.hold.size() > 2) {
                    System.out.println(bot.botIndex + " Error! Error!");
                }
                if(bot.hold.size() == 2) {
                    Iterator<Integer> iterator = bot.hold.iterator();
                    int low = iterator.next();
                    int high = iterator.next();

                    if(bot.lowIndex < OFFSET) {
                        bots.get(bot.lowIndex).hold.add(low);
                    } else {
                        outputs.put(bot.lowIndex - OFFSET, low);
                    }
                    if(bot.highIndex < OFFSET) {
                        bots.get(bot.highIndex).hold.add(high);
                    } else {
                        outputs.put(bot.highIndex - OFFSET, high);
                    }

                    bot.hold = new TreeSet<>();
                    continue outer;
                }
            }
        }

    }

    private static class Bot {

        int botIndex;
        int lowIndex;
        int highIndex;
        Set<Integer> hold = new TreeSet<>();

    }

}
