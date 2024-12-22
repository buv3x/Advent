package lt.mem.advent._2024;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class _2024_17 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/17.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }


    public static void first(List<String> input) {
        Computer computer = new Computer();
        computer.regA = Integer.parseInt(ReaderUtil.stringAfter(input.get(0), ": "));
        computer.regB = Integer.parseInt(ReaderUtil.stringAfter(input.get(1), ": "));
        computer.regC = Integer.parseInt(ReaderUtil.stringAfter(input.get(2), ": "));

        String[] split = StringUtils.split(ReaderUtil.stringAfter(input.get(4), ": "), ",");
        for (int i = 0; i < split.length / 2; ++i) {
            Instruction instruction = new Instruction(Operation.getByValue(Integer.parseInt(split[i * 2])),
                    Integer.parseInt(split[(i * 2) + 1]));
            computer.instructions.add(instruction);
        }

        boolean halted = false;
        while(!halted) {
            halted = computer.process();
        }

        System.out.println(StringUtils.join(computer.output, ","));

    }

    public static void second(List<String> input) {
        String targetValue = ReaderUtil.stringAfter(input.get(4), ": ");

        Computer computer = new Computer();
        String[] split = StringUtils.split(targetValue, ",");
        for (String s : split) {
            computer.targetOutput.add(Long.valueOf(s));
        }
        for (int i = 0; i < split.length / 2; ++i) {
            Instruction instruction = new Instruction(Operation.getByValue(Integer.parseInt(split[i * 2])),
                    Integer.parseInt(split[(i * 2) + 1]));
            computer.instructions.add(instruction);
        }

//        long i = (1L << (split.length - 1) * 3) * 6 + (1L << (split.length - 2) * 3) * 5 + (1L << (split.length - 3) * 3) * 5 - 1;
//        long i = (1L << 9) * 6 + (1L << 6) * 5;
//
//        for(int j = 0; j < 1000; ++j) {
//            cleanComputer(computer);
//            computer.regA = i;
//
//            boolean halted = false;
//            while(!halted) {
//                halted = computer.process();
//            }
//            String output = StringUtils.join(computer.output, ",");
//            System.out.println(i + ": " + output);
//            if(output.equals(targetValue)) {
//                break;
//            }
////            if(i % 100000000L == 0) {
////                System.out.println("Processing " + i / 100000000L);
////            }
//            i += 1L;
//        }

        LinkedList<Integer> values = new LinkedList<>();
        Long processed = process(computer, values);

        cleanComputer(computer);
        computer.regA = processed;
        boolean halted = false;
        while(!halted) {
            halted = computer.process();
        }
        System.out.println(computer.output);

        System.out.println(processed);
    }

    private static Long process(Computer computer, LinkedList<Integer> values) {
        long base = getBase(values);

        for(int i = 0; i < 8; ++i) {
            cleanComputer(computer);
            computer.regA = base + i;
            boolean halted = false;
            while(!halted) {
                halted = computer.process();
            }
            if(computer.output.get(0).equals(computer.targetOutput.get(computer.targetOutput.size() - 1 - values.size()))) {
                LinkedList<Integer> newValues = new LinkedList<>(values);
                newValues.addFirst(i);
                if(computer.targetOutput.size() - 1 - values.size() == 0) {
                    System.out.println(newValues);
                    return base + i;
                }

                Long processed = process(computer, newValues);
                if(processed != null) {
                    return processed;
                }
            }
        }
        return null;
    }

    private static long getBase(LinkedList<Integer> values) {
        long base = 0L;
        for (int i = values.size() - 1; i >= 0; --i) {
            base += (1L << 3 * (i + 1)) * values.get(i);
        }
        return base;
    }


    private static void cleanComputer(Computer computer) {
        computer.regB = 0;
        computer.regC = 0;
        computer.output = new LinkedList<>();
        computer.instructionIndex = 0;
    }

    private static class Computer{
        long regA;
        long regB;
        long regC;
        List<Instruction> instructions = new ArrayList<>();
        int instructionIndex = 0;
        LinkedList<Long> output = new LinkedList<>();
        LinkedList<Long> targetOutput = new LinkedList<>();

        long getCombo(int operand) {
            if(operand <= 3) {
                return operand;
            } else if(operand == 4) {
                return regA;
            } else if(operand == 5) {
                return regB;
            } else if(operand == 6) {
                return regC;
            }
            throw new IllegalArgumentException();
        }

        boolean process() {
            Instruction instruction = instructions.get(instructionIndex);
            instruction.operation.function.accept(instruction.operand, this);
            return instructionIndex >= instructions.size();
        }
    }


    private enum Operation {

        ADV(0, (operand, computer) -> {
            long power = computer.getCombo(operand);
            computer.regA = computer.regA / (1L << power);
            computer.instructionIndex++;
        }),
        BXL(1, (operand, computer) -> {
            computer.regB = computer.regB ^ operand;
            computer.instructionIndex++;
        }),
        BST(2, (operand, computer) -> {
            computer.regB = computer.getCombo(operand) % 8;
            computer.instructionIndex++;
        }),
        JNZ(3, (operand, computer) -> {
            if(computer.regA == 0) {
                computer.instructionIndex++;
            } else {
                computer.instructionIndex = operand;
            }
        }),
        BXC(4, (operand, computer) -> {
            computer.regB = computer.regB ^ computer.regC;
            computer.instructionIndex++;
        }),
        OUT(5, (operand, computer) -> {
            computer.output.add(computer.getCombo(operand) % 8);
//            if(computer.output.size() > computer.targetOutput.size()) {
//                computer.error = true;
//            } else {
//                if(!computer.output.get(computer.output.size() - 1).equals(computer.targetOutput.get(computer.output.size() - 1))) {
//                    computer.error = true;
//                }
//            }
            computer.instructionIndex++;
        }),
        BDV(6, (operand, computer) -> {
            long power = computer.getCombo(operand);
            computer.regB = computer.regA / (1L << power);
            computer.instructionIndex++;
        }),
        CDV(7, (operand, computer) -> {
            long power = computer.getCombo(operand);
            computer.regC = computer.regA / (1L << power);
            computer.instructionIndex++;
        });

        final int value;

        final BiConsumer<Integer, Computer> function;

        static Operation getByValue(int operation) {
            for (Operation value : Operation.values()) {
                if(value.value == operation) {
                    return value;
                }
            }
            return null;
        }

        Operation(int value, BiConsumer<Integer, Computer> function) {
            this.value = value;
            this.function = function;
        }
    }

    private static class Instruction {
        Operation operation;
        int operand;

        public Instruction(Operation operation, int operand) {
            this.operation = operation;
            this.operand = operand;
        }
    }

}
