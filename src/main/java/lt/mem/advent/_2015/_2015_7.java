package lt.mem.advent._2015;

import lt.mem.advent.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2015_7 {

    public static final String XXX = "XXX";

    private static final Map<String, Gate> gates = new HashMap<>();

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/7.txt");
//                first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {

        for (String line : lineInput) {
            String result = line.substring(line.indexOf("->") + "->".length()).trim();
            line = line.substring(0, line.indexOf("->")).trim();
            GateOperator operator;
            String leftOperator;
            String rightOperator;
            int functionValue;
            if(line.contains("AND")) {
                String first = ReaderUtil.stringBefore(line, " ");
                if(StringUtils.isNumeric(first)) {
                    operator = (l, r, v) -> {
                        List<Boolean> res = new ArrayList<>();
                        List<Boolean> bitParse = bitParse(v);
                        for (int i = 0; i < l.size(); ++i) {
                            res.add(l.get(i) && bitParse.get(i));
                        }
                        return res;
                    };
                    leftOperator = ReaderUtil.stringAfter(line, "AND ");
                    rightOperator = XXX;
                    functionValue = Integer.parseInt(first);
                } else {
                    operator = (l, r, v) -> {
                        List<Boolean> res = new ArrayList<>();
                        for (int i = 0; i < l.size(); ++i) {
                            res.add(l.get(i) && r.get(i));
                        }
                        return res;
                    };
                    leftOperator = ReaderUtil.stringBefore(line, " ");
                    rightOperator = ReaderUtil.stringAfter(line, "AND ");
                    functionValue = 0;
                }
            } else if(line.contains("OR")) {
                operator = (l, r, v) -> {
                    List<Boolean> res = new ArrayList<>();
                    for (int i = 0; i < l.size(); ++i) {
                        res.add(l.get(i) || r.get(i));
                    }
                    return res;
                };
                leftOperator = ReaderUtil.stringBefore(line, " ");
                rightOperator = ReaderUtil.stringAfter(line, "OR ");
                functionValue = 0;
            } else if(line.contains("RSHIFT")) {
                operator = (l, r, v) -> {
                    List<Boolean> res = new ArrayList<>();
                    for (int i = 0; i < v; ++i) {
                        res.add(false);
                    }
                    for (int i = v; i < l.size(); ++i) {
                        res.add(l.get(i - v));
                    }
                    return res;
                };
                leftOperator = ReaderUtil.stringBefore(line, " ");
                rightOperator = XXX;
                functionValue = Integer.parseInt(ReaderUtil.stringAfter(line, "RSHIFT "));
            } else if(line.contains("LSHIFT")) {
                operator = (l, r, v) -> {
                    List<Boolean> res = new ArrayList<>();
                    for (int i = v; i < l.size(); ++i) {
                        res.add(l.get(i));
                    }
                    for (int i = 0; i < v; ++i) {
                        res.add(false);
                    }
                    return res;
                };
                leftOperator = ReaderUtil.stringBefore(line, " ");
                rightOperator = XXX;
                functionValue = Integer.parseInt(ReaderUtil.stringAfter(line, "LSHIFT "));
            } else if(line.contains("NOT")) {
                operator = (l, r, v) -> {
                    List<Boolean> res = new ArrayList<>();
                    for (Boolean aBoolean : l) {
                        res.add(!aBoolean);
                    }
                    return res;
                };
                leftOperator = ReaderUtil.stringAfter(line, "NOT ");
                rightOperator = XXX;
                functionValue = 0;
            } else {
                if(StringUtils.isNumeric(line)) {
                    operator = (l, r, v) -> bitParse(v);
                    leftOperator = XXX;
                    rightOperator = XXX;
                    functionValue = Integer.parseInt(line);
                } else {
                    operator = (l, r, v) -> l;
                    leftOperator = line;
                    rightOperator = XXX;
                    functionValue = 0;
                }
            }
            gates.put(result, new Gate(leftOperator, rightOperator, functionValue, operator));
        }

        Gate gateA = gates.get("a");
        System.out.println(intParse(findValue(gateA, "a")));
    }

    public static void second(List<String> lineInput) {
        first(lineInput);
        List<Boolean> aResult = gates.get("a").result;
        for (Map.Entry<String, Gate> gateEntry : gates.entrySet()) {
            if(gateEntry.getKey().equals("b")) {
                gateEntry.getValue().result = new ArrayList<>(aResult);
            } else {
                gateEntry.getValue().result = null;
            }
        }
        Gate gateA = gates.get("a");
        System.out.println(intParse(findValue(gateA, "a")));
    }

    private static List<Boolean> findValue(Gate gate, String operand) {
        if(gate.result != null) {
            return gate.result;
        }
        System.out.println("Looking for " + operand + ": " + gate.leftOperand + " " + gate.rightOperand);
        List<Boolean> apply = gate.operator.apply(getValue(gate.leftOperand), getValue(gate.rightOperand), gate.functionValue);
        System.out.println(operand + " = " + apply);
        gate.result = apply;
        return apply;
    }

    private static List<Boolean> getValue(String operand) {
        if(operand.equals(XXX)) {
            List<Boolean> res = new ArrayList<>();
            for(int i = 0; i < 16; ++i) {
                res.add(false);
            }
            return res;
        }

        return findValue(gates.get(operand), operand);
    }

    private static List<Boolean> bitParse(int value) {
        List<Boolean> result = new ArrayList<>(16);
        String binString = StringUtils.leftPad(StringUtils.right(Integer.toBinaryString(value), 16), 16, '0');
        for (int i = 0; i < 16; ++i) {
            result.add(binString.charAt(i) == '1');
        }
        return result;
    }

    private static int intParse(List<Boolean> bitValues) {
        StringBuilder sb = new StringBuilder();
        for (Boolean bitValue : bitValues) {
            sb.append(bitValue ? "1": "0");
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    private static class Gate {

        private String leftOperand;

        private String rightOperand;

        private int functionValue;

        private List<Boolean> result;

        private GateOperator operator;

        public Gate(String leftOperand, String rightOperand, int functionValue, GateOperator operator) {
            this.leftOperand = leftOperand;
            this.rightOperand = rightOperand;
            this.functionValue = functionValue;
            this.operator = operator;
        }
    }

    @FunctionalInterface
    public interface GateOperator {

        List<Boolean> apply(List<Boolean> left, List<Boolean> right, int functionValue);
    }

}
