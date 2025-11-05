package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2017_16 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/16.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int BILLION = 1000000000;
    private static final int length = 16;
    private static List<Character> list = new ArrayList<>();
    static {
        for(int i = 0; i < length; ++i) {
            list.add((char) ('a' + i));
        }
    }

    public static void first(List<String> lineInput) {
        String[] splitted = StringUtils.split(lineInput.get(0), ',');

        List<Move> moves = new ArrayList<>();
        for (String moveString : splitted) {
            Move move = new Move();
            move.action = Action.getByCode(moveString.charAt(0));
            String values = moveString.substring(1);
            if(values.contains("/")) {
                if(move.action.equals(Action.EXCHANGE)) {
                    move.v1 = Integer.parseInt(ReaderUtil.stringBefore(values, "/"));
                    move.v2 = Integer.parseInt(ReaderUtil.stringAfter(values, "/"));
                } else {
                    move.c1 = values.charAt(0);
                    move.c2 = values.charAt(2);
                }
            } else {
                move.v1 = Integer.parseInt(values);
            }
            moves.add(move);
        }

        for (Move move : moves) {
            move.dance();
        }

        String sb = poseToString();
        System.out.println(sb);
    }

    private static String poseToString() {
        StringBuilder sb = new StringBuilder();
        for (Character character : list) {
            sb.append(character);
        }
        return sb.toString();
    }

    public static void second(List<String> lineInput) {
        String[] splitted = StringUtils.split(lineInput.get(0), ',');

        List<Move> moves = new ArrayList<>();
        for (String moveString : splitted) {
            Move move = new Move();
            move.action = Action.getByCode(moveString.charAt(0));
            String values = moveString.substring(1);
            if(values.contains("/")) {
                if(move.action.equals(Action.EXCHANGE)) {
                    move.v1 = Integer.parseInt(ReaderUtil.stringBefore(values, "/"));
                    move.v2 = Integer.parseInt(ReaderUtil.stringAfter(values, "/"));
                } else {
                    move.c1 = values.charAt(0);
                    move.c2 = values.charAt(2);
                }
            } else {
                move.v1 = Integer.parseInt(values);
            }
            moves.add(move);
        }

        Map<String, Integer> poses = new HashMap<>();
        int step = 0;
        while (!poses.containsKey(poseToString())) {
            poses.put(poseToString(), step);
            for (Move move : moves) {
                move.dance();
            }
            step++;
        }

        System.out.println(step);
        System.out.println(poses.get(poseToString()));


        int cycle = poses.get(poseToString()) - step;


        int startingMove = cycle * (BILLION / cycle);
        System.out.println(startingMove);

        for(int i = startingMove; i < BILLION; ++i) {
            for (Move move : moves) {
                move.dance();
            }
        }
        System.out.println(poseToString());
    }

    private static class Move {

        Action action;
        int v1;
        int v2;
        char c1;
        char c2;

        public void dance() {
            if(action.equals(Action.SPIN)) {
                List<Character> newList = new ArrayList<>();
                for(int i = list.size() - v1; i < list.size(); ++i) {
                    newList.add(list.get(i));
                }
                for (int i = 0; i < list.size() - v1; ++i) {
                    newList.add(list.get(i));
                }
                list = newList;
            } else if(action.equals(Action.EXCHANGE)) {
                exchange(v1, v2);
            } else {
                int foundV1 = 0;
                int foundV2 = 0;
                for(int i = 0; i < list.size(); ++i) {
                    if(list.get(i) == c1) {
                        foundV1 = i;
                    }
                    if(list.get(i) == c2) {
                        foundV2 = i;
                    }
                }
                exchange(foundV1, foundV2);
            }
        }

        private void exchange(int v1, int v2) {
            char temp = list.get(v1);
            list.set(v1, list.get(v2));
            list.set(v2, temp);
        }
    }

    private enum Action {
        SPIN('s'),
        EXCHANGE('x'),
        PARTNER('p');

        private final char code;

        Action(char code) {
            this.code = code;
        }

        static Action getByCode(char code) {
            for (Action value : Action.values()) {
                if(value.code == code) {
                    return value;
                }
            }
            return null;
        }
    }


}
