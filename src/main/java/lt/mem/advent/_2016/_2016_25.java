package lt.mem.advent._2016;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2016_25 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/25.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<String, Long> registers = new HashMap<>();

    private static final List<Command> commands = new ArrayList<>();

    private static int outers = 0;

    public static void first(List<String> lineInput) {
        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        parse(lineInput);

//        for(int i = 0; i < 100; ++i) {
//            run(i);
//        }
//        System.out.println();

        run(5);
        run(5 + (1<<1));
        run(5 + (1<<1) + (1<<3));
        run(5 + (1<<1) + (1<<3) + (1<<5));
        run(5 + (1<<1) + (1<<3) + (1<<5) + (1<<7));

        System.out.println(5 + (1<<1) + (1<<3) + (1<<5) + (1<<7));

//        run(5 + (1<<1 + 1<<3 + 1<<5 + 1<<7));
//
//        System.out.println(1<<1 + 1<<3 + 1<<5 + 1<<7);

    }

    private static void run(int i) {
        System.out.print("a = " + StringUtils.leftPad(i + "", 3, ' ') + ": ");
        registers.put("a", (long) i);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        outers = 0;
        int index = 0;
        while (index >= 0 && index < commands.size()) {
//            System.out.println(index + ") a: " + registers.get("a") + ", b: " + registers.get("b") + ", c: " + registers.get("c") + ", d: " + registers.get("d"));
            index = commands.get(index).apply(index);
        }
    }

    private static void parse(List<String> lineInput) {
        commands.clear();
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
    }


    public static void second(List<String> lineInput) {
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
                    if(secondRegister != null) {
                        if (firstRegister != null) {
                            registers.put(secondRegister, registers.get(firstRegister));
                        } else {
                            registers.put(secondRegister, firstValue);
                        }
                    }
                    return index + 1;
                }
                case JUMP: {
                    int offset = (int) (secondRegister != null ? registers.get(secondRegister) : secondValue);
                    if(firstRegister != null) {
                        if(!registers.get(firstRegister).equals(0L)) {
                            return index + offset;
                        }
                    } else {
                        if(firstValue != 0L) {
                            return index + offset;
                        }
                    }
                    return index + 1;
                }
                case TOGGLE: {
                    int offset = (int) (firstRegister != null ? registers.get(firstRegister) : firstValue);
                    int toggleIndex = index + offset;
                    if(toggleIndex >= 0 && toggleIndex < commands.size()) {
                        Command command = commands.get(toggleIndex);
                        if(command.action.equals(Action.TOGGLE) || command.action.equals(Action.DECREASE)) {
                            command.action = Action.INCREASE;
                        } else if(command.action.equals(Action.INCREASE)) {
                            command.action = Action.DECREASE;
                        } else if(command.action.equals(Action.COPY)) {
                            command.action = Action.JUMP;
                        } else if(command.action.equals(Action.JUMP)) {
                            command.action = Action.COPY;
                        }
                    }
                    return index + 1;
                }
                case OUT: {
                    System.out.print(firstRegister != null ? registers.get(firstRegister) : firstValue);
                    outers++;
                    if(outers > 100) {
                        System.out.println();
                        return index + 1000;
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
        JUMP("jnz"),
        TOGGLE("tgl"),
        OUT("out");

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
