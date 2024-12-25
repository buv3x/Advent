package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class _2024_25 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/25.txt");
        long timeMillis = System.currentTimeMillis();
        first(input);
//        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final List<List<Integer>> locks = new ArrayList<>();
    private static final List<List<Integer>> keys = new ArrayList<>();

    private static final String SEQUENCE = "#####";

    public static void first(List<String> input) {
        int size = 0;

        List<String> lockOrKey = new ArrayList<>();
        for (String row : input) {
            if(StringUtils.isNotBlank(row)) {
                lockOrKey.add(row);
            } else {
                parseLockOrKey(lockOrKey);
                lockOrKey.clear();
            }
        }
        parseLockOrKey(lockOrKey);
        size = lockOrKey.size() - 2;

        System.out.println(locks);
        System.out.println(keys);

        int total = 0;
        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean valid = true;
                for(int i = 0; i < SEQUENCE.length(); ++i) {
                    if(lock.get(i) + key.get(i) > size) {
                        valid = false;
                        break;
                    }
                }
                if(valid) {
                    total++;
                }
            }
        }
        System.out.println(total);

    }

    private static void parseLockOrKey(List<String> lockOrKey) {
        boolean lock = lockOrKey.get(0).equals(SEQUENCE);
        List<Integer> values = new ArrayList<>();
        if(lock) {
            for(int j = 0; j < SEQUENCE.length(); ++j) {
                for (int i = 1; i < lockOrKey.size(); ++i) {
                    if(lockOrKey.get(i).charAt(j) == '.') {
                        values.add(i - 1);
                        break;
                    }
                }
            }
            locks.add(values);
        } else {
            for(int j = 0; j < SEQUENCE.length(); ++j) {
                for (int i = lockOrKey.size() - 2; i >= 0; --i) {
                    if(lockOrKey.get(i).charAt(j) == '.') {
                        values.add(lockOrKey.size() - i - 2);
                        break;
                    }
                }
            }
            keys.add(values);
        }
    }

    public static void second(List<String> input) {

    }

}
