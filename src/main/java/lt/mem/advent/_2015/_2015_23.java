package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class _2015_23 {

    private static final List<Command> commands = new ArrayList<>();

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/23.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        parseInput(lineInput);

        State state = new State();
        while(state.statement >= 0 && state.statement <= commands.size() - 1) {
            Command command = commands.get(state.statement);
            command.action.action.accept(command, state);
        }

        System.out.println();
        System.out.println(state.registers.get("b"));

    }

    private static void parseInput(List<String> lineInput) {
        for (String line : lineInput) {
            String actionLine = ReaderUtil.stringBefore(line, " ");
            if(actionLine.equals("hlf")) {
                String register = ReaderUtil.stringAfter(line, " ");
                commands.add(new Command(Action.HALF, register, 0));
            } else if(actionLine.equals("tpl")) {
                String register = ReaderUtil.stringAfter(line, " ");
                commands.add(new Command(Action.TRIPLE, register, 0));
            } else if(actionLine.equals("inc")) {
                String register = ReaderUtil.stringAfter(line, " ");
                commands.add(new Command(Action.INCREMENT, register, 0));
            } else if(actionLine.equals("jmp")) {
                int value = Integer.parseInt(ReaderUtil.stringAfter(line, " "));
                commands.add(new Command(Action.JUMP, "a", value));
            } else if(actionLine.equals("jie")) {
                String register = ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, " "), ", ");
                int value = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringAfter(line, " "), ", "));
                commands.add(new Command(Action.JUMP_EVEN, register, value));
            } else if(actionLine.equals("jio")) {
                String register = ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, " "), ", ");
                int value = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringAfter(line, " "), ", "));
                commands.add(new Command(Action.JUMP_ONE, register, value));
            }
        }
    }


    public static void second(List<String> lineInput) {
        parseInput(lineInput);

        State state = new State();
        state.registers.put("a", BigInteger.ONE);
        while(state.statement >= 0 && state.statement <= commands.size() - 1) {
            Command command = commands.get(state.statement);
            command.action.action.accept(command, state);
        }

        System.out.println();
        System.out.println(state.registers.get("b"));
    }



    private enum Action {
        HALF((c, s) -> {
            s.registers.put(c.register, s.registers.get(c.register).divide(BigInteger.TWO));
            s.statement++;
        }),
        TRIPLE((c, s) -> {
            s.registers.put(c.register, s.registers.get(c.register).multiply(BigInteger.valueOf(3L)));
            s.statement++;
        }),
        INCREMENT((c, s) -> {
            s.registers.put(c.register, s.registers.get(c.register).add(BigInteger.ONE));
            s.statement++;
        }),
        JUMP((c, s) -> s.statement += c.value),
        JUMP_EVEN((c, s) -> {
            if(s.registers.get(c.register).mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                s.statement += c.value;
            } else {
                s.statement++;
            }
        }),
        JUMP_ONE((c, s) -> {
            if(s.registers.get(c.register).equals(BigInteger.ONE)) {
                s.statement += c.value;
            } else {
                System.out.println(s.registers.get(c.register));
                s.statement++;
            }
        });

        final BiConsumer<Command, State> action;

        Action(BiConsumer<Command, State> action) {
            this.action = action;
        }
    }

    private static class Command {
        Action action;
        String register;
        int value;

        public Command(Action action, String register, int value) {
            this.action = action;
            this.register = register;
            this.value = value;
        }
    }

    private static class State {
        Map<String, BigInteger> registers = new HashMap<>(Map.of("a", BigInteger.ZERO, "b", BigInteger.ZERO));
        int statement = 0;
    }

}
