package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _2015_11 {


    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/11_ex.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        char[] input = lineInput.get(0).toCharArray();
        while (!isValid(input)) {
            input = increment(input);
        }
        System.out.println(input);
    }

    public static void second(List<String> lineInput) {
        char[] input = lineInput.get(0).toCharArray();
        while (!isValid(input)) {
            input = increment(input);
        }
        input = increment(input);
        while (!isValid(input)) {
            input = increment(input);
        }
        System.out.println(input);
    }

    private static char[] increment(char[] input) {
        char[] result = new char[input.length];
        boolean carry = true;
        for(int i = input.length - 1; i >= 0; --i) {
            if(!carry) {
                result[i] = input[i];
            } else {
                if (input[i] == 'z') {
                    result[i] = 'a';
                } else {
                    result[i] = (char) (input[i] + 1);
                    carry = false;
                }
            }
        }
        return result;
    }

    private static boolean isValid(char[] input) {
        for (char c : input) {
            if(c == 'i' || c == 'o' || c == 'l') {
                return false;
            }
        }

        Set<Integer> positions = new HashSet<>();
        for(int i = 0; i < input.length - 1; ++i) {
            if(input[i] == input[i + 1]) {
                positions.add(i);
                positions.add(i + 1);
            }
        }
        if(positions.size() < 4) {
            return false;
        }

        int run = 1;
        for(int i = 1; i < input.length; ++i) {
            if(input[i] - input[i - 1] == 1) {
                run++;
                if(run == 3) {
                    return true;
                }
            } else {
                run = 1;
            }
        }

        return false;
    }

}
