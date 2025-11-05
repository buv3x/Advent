package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _2016_7 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/7.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int count = 0;
        outer: for (String row : lineInput) {
            List<String> addresses = new ArrayList<>();
            List<String> hypernets = new ArrayList<>();
            String[] split = StringUtils.split(row, "[");
            for (String s : split) {
                if (s.contains("]")) {
                    hypernets.add(ReaderUtil.stringBefore(s, "]"));
                    addresses.add(ReaderUtil.stringAfter(s, "]"));
                } else {
                    addresses.add(s);
                }
            }

            for (String hypernet : hypernets) {
                if(hasAbba(hypernet)) {
                    continue outer;
                }
            }

            for (String address : addresses) {
                if(hasAbba(address)) {
                    count++;
                    continue outer;
                }
            }

        }
        System.out.println(count);
    }

    private static boolean hasAbba(String value) {
        for(int i = 0; i <= value.length() - 4; ++i) {
            if(value.charAt(i) == value.charAt(i + 3) && value.charAt(i + 1) == value.charAt(i + 2) && value.charAt(i) != value.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    public static void second(List<String> lineInput) {
        int count = 0;
        outer: for (String row : lineInput) {
            List<String> addresses = new ArrayList<>();
            List<String> hypernets = new ArrayList<>();
            String[] split = StringUtils.split(row, "[");
            for (String s : split) {
                if (s.contains("]")) {
                    hypernets.add(ReaderUtil.stringBefore(s, "]"));
                    addresses.add(ReaderUtil.stringAfter(s, "]"));
                } else {
                    addresses.add(s);
                }
            }

            Set<Pair<Character, Character>> pairs = new HashSet<>();
            for (String address : addresses) {
                for(int i = 0; i <= address.length() - 3; ++i) {
                    if(address.charAt(i) == address.charAt(i + 2) && address.charAt(i + 1) != address.charAt(i)) {
                        pairs.add(Pair.of(address.charAt(i), address.charAt(i + 1)));
                    }
                }
            }

//            System.out.println(pairs);

            for (String hypernet : hypernets) {
                for (Pair<Character, Character> pair : pairs) {
                    for(int i = 0; i <= hypernet.length() - 3; ++i) {
                        if(hypernet.charAt(i) == hypernet.charAt(i + 2) && hypernet.charAt(i) == pair.getRight() && hypernet.charAt(i + 1) == pair.getLeft()) {
                            count++;
                            continue outer;
                        }
                    }
                }
            }


        }
        System.out.println(count);
    }

}
