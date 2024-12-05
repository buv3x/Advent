package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2024_1 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/1.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();
        for (String line : input) {
            firstList.add(Integer.parseInt(ReaderUtil.stringBefore(line, "   ")));
            secondList.add(Integer.parseInt(ReaderUtil.stringAfter(line, "   ")));
        }
        Collections.sort(firstList);
        Collections.sort(secondList);

        int total = 0;
        for(int i = 0; i < firstList.size(); ++i) {
            total += Math.abs(firstList.get(i) - secondList.get(i));
        }

        System.out.println(total);

    }

    public static void second(List<String> input) {
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();
        for (String line : input) {
            firstList.add(Integer.parseInt(ReaderUtil.stringBefore(line, "   ")));
            secondList.add(Integer.parseInt(ReaderUtil.stringAfter(line, "   ")));
        }

        int total = 0;
        for (Integer element : firstList) {
            int count = 0;
            for (Integer inclusion : secondList) {
                if(inclusion.equals(element)) {
                    count++;
                }
            }
            total += count * element;
        }

        System.out.println(total);
    }

}
