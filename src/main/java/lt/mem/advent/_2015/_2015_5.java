package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;

public class _2015_5 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/5.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int total = 0;
        for (String input : lineInput) {
            if((input.length() - input.replace("a", "")
                    .replace("e", "")
                    .replace("i", "")
                    .replace("o", "")
                    .replace("u", "").length()) < 3) {
                continue;
            }
            boolean valid = false;
            for(int i = 0; i < input.length() - 1; i++) {
                if(input.charAt(i) == input.charAt(i + 1)) {
                    valid = true;
                    break;
                }
            }
            if(!valid) {
                continue;
            }
            if(input.contains("ab") || input.contains("cd") || input.contains("pq") || input.contains("xy")) {
                continue;
            }

            System.out.println(input);
            total++;
        }
        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        int total = 0;
        for (String input : lineInput) {
            boolean valid = false;
            for(int i = 0; i < input.length() - 2; i++) {
                if(input.charAt(i) == input.charAt(i + 2)) {
                    valid = true;
                    break;
                }
            }
            if(!valid) {
                continue;
            }

            valid = false;
            for(int i = 0; i < input.length() - 1; i++) {
                String sub = input.substring(i, i + 2);
                if(input.length() - input.replace(sub, "").length() > 2) {
                    valid = true;
                    break;
                }
            }
            if(!valid) {
                continue;
            }

            System.out.println(input);
            total++;
        }
        System.out.println(total);
    }


}
