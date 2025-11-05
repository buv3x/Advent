package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2017_08 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/8.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static List<Command> commands = new ArrayList<>();
    private final static Map<String, Integer> registers = new HashMap<>();

    private static int maxValue = Integer.MIN_VALUE;

    public static void first(List<String> lineInput) {
        parse(lineInput);

        for (Command command : commands) {
            command.apply();
        }

        System.out.println(registers);
        System.out.println(registers.values().stream().max(Integer::compareTo).get());

    }

    private static Integer safeGet(String register) {
        if(!registers.containsKey(register)) {
            registers.put(register, 0);
        }

        return registers.get(register);
    }

    private static void parse(List<String> lineInput) {
        for (String row : lineInput) {
            String commandString = ReaderUtil.stringBefore(row, " if ");
            String conditionString = ReaderUtil.stringAfter(row, " if ");
            String[] commandSplit = StringUtils.split(commandString, ' ');
            String[] conditionSplit = StringUtils.split(conditionString, ' ');
            Condition condition = new Condition();
            condition.check = Check.getByCode(conditionSplit[1]);
            condition.register = conditionSplit[0];
            condition.value = Integer.parseInt(conditionSplit[2]);

            Command command = new Command();
            command.action = Action.getByCode(commandSplit[1]);
            command.condition = condition;
            command.register = commandSplit[0];
            command.value = Integer.parseInt(commandSplit[2]);
            commands.add(command);
        }
    }

    public static void second(List<String> lineInput) {
        parse(lineInput);

        for (Command command : commands) {
            command.apply();
        }

        System.out.println(maxValue);
    }

    private static class Command {
        Action action;
        int value;
        String register;
        Condition condition;

        void apply() {
            if(condition.check()) {
                Integer registerValue = safeGet(register);
                switch (action) {
                    case INC: {
                        registerValue += value;
                        break;
                    }
                    case DEC: {
                        registerValue -= value;
                        break;
                    }
                }
                if(registerValue > maxValue) {
                    maxValue = registerValue;
                }
                registers.put(register, registerValue);
            }
        }
    }

    private enum Action {

        INC("inc"),
        DEC("dec");

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


    private static class Condition {
        Check check;
        String register;
        int value;

        boolean check() {
            Integer registerValue = safeGet(register);
            switch (check) {
                case EQ: return registerValue.equals(value);
                case NEQ: return !registerValue.equals(value);
                case GT: return registerValue > value;
                case LS: return registerValue < value;
                case GTE: return registerValue >= value;
                case LSE: return registerValue <= value;
            }
            return false;
        }
    }

    private enum Check {
        GT(">"),
        LS("<"),
        GTE(">="),
        LSE("<="),
        EQ("=="),
        NEQ("!=");

        final String code;


        Check(String code) {
            this.code = code;
        }

        static Check getByCode(String code) {
            for (Check value : Check.values()) {
                if(value.code.equals(code)) {
                    return value;
                }
            }
            return null;
        }
    }

}
