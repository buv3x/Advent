package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2017_18 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/18.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Character, Long> registers = new HashMap<>();

    private static Long getRegisterValue(char register) {
        if(!registers.containsKey(register)) {
            registers.put(register, 0L);
        }
        return registers.get(register);
    }

    private static int position = 0;

    private static Long lastSound = 0L;

    public static void first(List<String> lineInput) {
        List<Move> moves = new ArrayList<>();
        for (String line : lineInput) {
            Move move = new Move();
            String actionString = ReaderUtil.stringBefore(line, " ");
            String variableString = ReaderUtil.stringAfter(line, " ");
            move.action = Action.getByCode(actionString);
            if(variableString.contains(" ")) {
                String firstVar = ReaderUtil.stringBefore(variableString, " ");
                String secondVar = ReaderUtil.stringAfter(variableString, " ");
                if(firstVar.length() == 1 && firstVar.charAt(0) >= 'a' && firstVar.charAt(0) <= 'z') {
                    move.c1 = firstVar.charAt(0);
                } else {
                    move.v1 = Integer.parseInt(firstVar);
                }
                if(secondVar.length() == 1 && secondVar.charAt(0) >= 'a' && secondVar.charAt(0) <= 'z') {
                    move.c2 = secondVar.charAt(0);
                } else {
                    move.v2 = Integer.parseInt(secondVar);
                }
            } else {
                if(variableString.length() == 1 && variableString.charAt(0) >= 'a' && variableString.charAt(0) <= 'z') {
                    move.c1 = variableString.charAt(0);
                } else {
                    move.v1 = Integer.parseInt(variableString);
                }
            }

            moves.add(move);
        }

        boolean stop = false;
        while(!stop) {
            stop = moves.get(position).play();
        }

        System.out.println(lastSound);
    }

    private static final Map<String, Map<Character, Long>> registers2 = new HashMap<>();
    private static Map<String, Long> position2 = new HashMap<>();
    private static Map<String, Queue<Long>> queue2 = new HashMap<>();
    private static Map<String, Integer> sendCount = new HashMap<>();

    private static Long getRegisterValue2(String id, char register) {
        if(!registers2.get(id).containsKey(register)) {
            registers2.get(id).put(register, 0L);
        }
        return registers2.get(id).get(register);
    }

    public static void second(List<String> lineInput) {
        Map<Character, Long> registersA = new HashMap<>();
        registersA.put('p', 0L);
        registers2.put("A", registersA);

        Map<Character, Long> registersB = new HashMap<>();
        registersB.put('p', 1L);
        registers2.put("B", registersB);

        position2.put("A", 0L);
        position2.put("B", 0L);

        queue2.put("A", new LinkedList<>());
        queue2.put("B", new LinkedList<>());

        sendCount.put("A", 0);
        sendCount.put("B", 0);


        List<Move2> movesA = new ArrayList<>();
        List<Move2> movesB = new ArrayList<>();
        for (String line : lineInput) {
            Move2 moveA = parseMove(line);
            moveA.id = "A";
            moveA.recId = "B";
            movesA.add(moveA);

            Move2 moveB = parseMove(line);
            moveB.id = "B";
            moveB.recId = "A";
            movesB.add(moveB);
        }

        boolean deadlock = false;
        while(!deadlock) {
            boolean deadlockA;
            Long positionA = position2.get("A");
            if(positionA >= movesA.size()) {
                deadlockA = true;
            } else {
                deadlockA = movesA.get(positionA.intValue()).play();
            }

            boolean deadlockB;
            Long positionB = position2.get("B");
            if(positionB >= movesB.size()) {
                deadlockB = true;
            } else {
                deadlockB = movesB.get(positionB.intValue()).play();
            }


            deadlock = deadlockB && deadlockA;
        }

        System.out.println(sendCount.get("A"));
        System.out.println(sendCount.get("B"));
    }

    private static Move2 parseMove(String line) {
        Move2 move = new Move2();
        String actionString = ReaderUtil.stringBefore(line, " ");
        String variableString = ReaderUtil.stringAfter(line, " ");
        move.action = Action.getByCode(actionString);
        if(variableString.contains(" ")) {
            String firstVar = ReaderUtil.stringBefore(variableString, " ");
            String secondVar = ReaderUtil.stringAfter(variableString, " ");
            if(firstVar.length() == 1 && firstVar.charAt(0) >= 'a' && firstVar.charAt(0) <= 'z') {
                move.c1 = firstVar.charAt(0);
            } else {
                move.v1 = Integer.parseInt(firstVar);
            }
            if(secondVar.length() == 1 && secondVar.charAt(0) >= 'a' && secondVar.charAt(0) <= 'z') {
                move.c2 = secondVar.charAt(0);
            } else {
                move.v2 = Integer.parseInt(secondVar);
            }
        } else {
            if(variableString.length() == 1 && variableString.charAt(0) >= 'a' && variableString.charAt(0) <= 'z') {
                move.c1 = variableString.charAt(0);
            } else {
                move.v1 = Integer.parseInt(variableString);
            }
        }
        return move;
    }

    private static class Move2 {

        Action action;

        long v1;
        long v2;
        char c1 = '#';
        char c2 = '#';
        String id;
        String recId;

        public boolean play() {
            if(action.equals(Action.SOUND)) {
                queue2.get(recId).add(getValue(v1, c1));
                sendCount.put(id, sendCount.get(id) + 1);
            } else if(action.equals(Action.SET)) {
                long value = getValue(v2, c2);
                registers2.get(id).put(c1, value);
            } else if(action.equals(Action.ADD)) {
                long value = getValue(v2, c2);
                registers2.get(id).put(c1, getRegisterValue2(id, c1) + value);
            } else if(action.equals(Action.MULTIPLY)) {
                long value = getValue(v2, c2);
                registers2.get(id).put(c1, getRegisterValue2(id, c1) * value);
            } else if(action.equals(Action.MOD)) {
                long value = getValue(v2, c2);
                registers2.get(id).put(c1, getRegisterValue2(id, c1) % value);
            } else if(action.equals(Action.RECOVER)) {
                if(queue2.get(id).size() == 0) {
                    return true;
                }
                Long value = queue2.get(id).poll();
                registers2.get(id).put(c1, value);
            } else {
                long value = getValue(v1, c1);
                if(value > 0) {
                    long offset = getValue(v2, c2);
                    position2.put(id, position2.get(id) + offset);
                    return false;
                }
            }

            position2.put(id, position2.get(id) + 1);
            return false;
        }

        private long getValue(long v, char c) {
            long value;
            if (c == '#') {
                value = v;
            } else {
                value = getRegisterValue2(id, c);
            }
            return value;
        }
    }

    private static class Move {

        Action action;

        long v1;
        long v2;
        char c1 = '#';
        char c2 = '#';

        public boolean play() {
            if(action.equals(Action.SOUND)) {
                lastSound = getValue(v1, c1);
            } else if(action.equals(Action.SET)) {
                long value = getValue(v2, c2);
                registers.put(c1, value);
            } else if(action.equals(Action.ADD)) {
                long value = getValue(v2, c2);
                registers.put(c1, getRegisterValue(c1) + value);
            } else if(action.equals(Action.MULTIPLY)) {
                long value = getValue(v2, c2);
                registers.put(c1, getRegisterValue(c1) * value);
            } else if(action.equals(Action.MOD)) {
                long value = getValue(v2, c2);
                registers.put(c1, getRegisterValue(c1) % value);
            } else if(action.equals(Action.RECOVER)) {
                long value = getValue(v1, c1);
                if(value != 0) {
                    return true;
                }
            } else {
                long value = getValue(v1, c1);
                if(value > 0) {
                    long offset = getValue(v2, c2);
                    position += offset;
                    return false;
                }
            }

            position++;
            return false;
        }

        private long getValue(long v, char c) {
            long value;
            if (c == '#') {
                value = v;
            } else {
                value = getRegisterValue(c);
            }
            return value;
        }
    }

    private enum Action {
        SOUND("snd"),
        SET("set"),
        ADD("add"),
        MULTIPLY("mul"),
        MOD("mod"),
        RECOVER("rcv"),
        JUMP("jgz");

        private final String code;

        Action(String code) {
            this.code = code;
        }

        static Action getByCode(String code) {
            for (Action value : Action.values()) {
                if(value.code.equals(code)) {
                    return value;
                }
            }
            return null;
        }
    }


}
