package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2016_12 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/12.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<String, Long> registers = new HashMap<>();

    public static void first(List<String> lineInput) {
        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        List<Command> commands = parse(lineInput);
        int index = 0;
        long steps = 0;
        while(index >= 0 && index < commands.size()) {
//            System.out.println(index + ") a: " + registers.get("a") + ", b: " + registers.get("b") + ", c: " + registers.get("c") + ", d: " + registers.get("d"));
            index = commands.get(index).apply(index);
            steps++;
//            if(steps >= 500) {
//                break;
//            }
        }
        System.out.println(registers.get("a"));
    }

    private static List<Command> parse(List<String> lineInput) {
        List<Command> commands = new ArrayList<>();
        for (String row : lineInput) {
            String actionCode = ReaderUtil.stringBefore(row, " ");
            Action action = Action.getByCode(actionCode);
            String operands = ReaderUtil.stringAfter(row, " ");
            Command command = new Command();
            command.action = action;
            if(operands.contains(" ")) {
                String firstOperand = ReaderUtil.stringBefore(operands, " ");
                String secondOperand = ReaderUtil.stringAfter(operands, " ");
                if(!registers.containsKey(firstOperand)) {
                    command.firstValue = Long.parseLong(firstOperand);
                } else {
                    command.firstRegister = firstOperand;
                }
                if(!registers.containsKey(secondOperand)) {
                    command.secondValue = Long.parseLong(secondOperand);
                } else {
                    command.secondRegister = secondOperand;
                }
            } else {
                if(!registers.containsKey(operands)) {
                    command.firstValue = Long.parseLong(operands);
                } else {
                    command.firstRegister = operands;
                }
            }
            commands.add(command);
        }
        return commands;
    }

    public static void second(List<String> lineInput) {
        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 1L);
        registers.put("d", 0L);
        List<Command> commands = parse(lineInput);
        int index = 0;
        while(index >= 0 && index < commands.size()) {
            index = commands.get(index).apply(index);
        }
        System.out.println(registers.get("a"));
    }

    private static class Command {

        Action action;
        String firstRegister;
        long firstValue;
        String secondRegister;
        long secondValue;

        int apply(int index) {
            switch (action) {
                case INCREASE: {
                    registers.merge(firstRegister, 1L, Long::sum);
                    return index + 1;
                }
                case DECREASE: {
                    registers.merge(firstRegister, -1L, Long::sum);
                    return index + 1;
                }
                case COPY: {
                    if(firstRegister != null) {
                        registers.put(secondRegister, registers.get(firstRegister));
                    } else {
                        registers.put(secondRegister, firstValue);
                    }
                    return index + 1;
                }
                case JUMP: {
                    if(firstRegister != null) {
                        if(!registers.get(firstRegister).equals(0L)) {
                            return index + (int) secondValue;
                        }
                    } else {
                        if(firstValue != 0L) {
                            return index + (int) secondValue;
                        }
                    }
                    return index + 1;
                }
            }

            return index + 1;
        }
    }

    private enum Action {
        COPY("cpy"),
        INCREASE("inc"),
        DECREASE("dec"),
        JUMP("jnz");

        final String code;

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
