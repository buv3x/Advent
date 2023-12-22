package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class _2023_19 {

    private static final Map<String, Workflow> workflowMap = new HashMap<>();

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/19.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        boolean isItems = false;
        List<Item> items = new ArrayList<>();
        for (String line : input) {
            if(StringUtils.isBlank(line)) {
                isItems = true;
                continue;
            }
            if(isItems) {
                String[] itemStrings = StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "{"), "}"), ",");
                int x = Integer.parseInt(ReaderUtil.stringAfter(itemStrings[0], "="));
                int m = Integer.parseInt(ReaderUtil.stringAfter(itemStrings[1], "="));
                int a = Integer.parseInt(ReaderUtil.stringAfter(itemStrings[2], "="));
                int s = Integer.parseInt(ReaderUtil.stringAfter(itemStrings[3], "="));
                items.add(new Item(x, m ,a, s));
            } else {
                String name = ReaderUtil.stringBefore(line, "{");
                String[] ruleStrings = StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "{"), "}"), ",");
                Workflow workflow = new Workflow(name);
                for (String ruleString : ruleStrings) {
                    WorkflowRule rule = new WorkflowRule();
                    if(ruleString.contains(":")) {
                        String returnString = ReaderUtil.stringAfter(ruleString, ":");
                        parseReturn(returnString, rule);
                        if(ruleString.contains(">")) {
                            rule.check = Check.GREATER;
                            String property = ReaderUtil.stringBefore(ruleString, ">");
                            parseProperty(property, rule);
                            rule.value = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(ruleString, ">"), ":"));
                        } else {
                            rule.check = Check.SMALLER;
                            String property = ReaderUtil.stringBefore(ruleString, "<");
                            parseProperty(property, rule);
                            rule.value = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(ruleString, "<"), ":"));
                        }
                    } else {
                        rule.check = Check.AUTO;
                        parseReturn(ruleString, rule);
                    }
                    workflow.rules.add(rule);
                }
                workflowMap.put(name, workflow);
            }
        }

        Workflow inWorkflow = workflowMap.get("in");
        long total = 0;
        for (Item item : items) {
            boolean apply = inWorkflow.apply(item);
            if(apply) {
                total += item.sum();
            }
//            System.out.println(apply);
        }
        System.out.println(total);
    }

    private static void parseProperty(String property, WorkflowRule rule) {
        if(property.equals("x")) {
            rule.getter = Item::getX;
        } else if(property.equals("m")) {
            rule.getter = Item::getM;
        } else if(property.equals("a")) {
            rule.getter = Item::getA;
        } else {
            rule.getter = Item::getS;
        }
    }

    private static void parseProperty2(String property, WorkflowRule rule) {
        if(property.equals("x")) {
            rule.property = Property.X;
        } else if(property.equals("m")) {
            rule.property = Property.M;
        } else if(property.equals("a")) {
            rule.property = Property.A;
        } else {
            rule.property = Property.S;
        }
    }

    private static void parseReturn(String returnString, WorkflowRule rule) {
        if(returnString.equals("R")) {
            rule.returnValue = false;
        } else if(returnString.equals("A")) {
            rule.returnValue = true;
        } else {
            rule.redirect = returnString;
        }
    }


    public static void second(List<String> input) {
        for (String line : input) {
            if(StringUtils.isBlank(line)) {
                break;
            }
            String name = ReaderUtil.stringBefore(line, "{");
            String[] ruleStrings = StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "{"), "}"), ",");
            Workflow workflow = new Workflow(name);
            for (String ruleString : ruleStrings) {
                WorkflowRule rule = new WorkflowRule();
                if(ruleString.contains(":")) {
                    String returnString = ReaderUtil.stringAfter(ruleString, ":");
                    parseReturn(returnString, rule);
                    if(ruleString.contains(">")) {
                        rule.check = Check.GREATER;
                        String property = ReaderUtil.stringBefore(ruleString, ">");
                        parseProperty2(property, rule);
                        rule.value = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(ruleString, ">"), ":"));
                    } else {
                        rule.check = Check.SMALLER;
                        String property = ReaderUtil.stringBefore(ruleString, "<");
                        parseProperty2(property, rule);
                        rule.value = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(ruleString, "<"), ":"));
                    }
                } else {
                    rule.check = Check.AUTO;
                    parseReturn(ruleString, rule);
                }
                workflow.rules.add(rule);
            }
            workflowMap.put(name, workflow);
        }

        Workflow inWorkflow = workflowMap.get("in");
        System.out.println(inWorkflow.apply(new ItemRange()));
    }

    private static class Workflow {
        final String name;
        List<WorkflowRule> rules = new ArrayList<>();

        public Workflow(String name) {
            this.name = name;
        }

        long apply(ItemRange itemRange) {
            long total = 0;
            ItemRange remaining = itemRange;
            for (WorkflowRule rule : rules) {
                if(remaining != null) {
                    Pair<ItemRange, ItemRange> pair = rule.applyCheck(remaining);
                    if(pair.getLeft() != null) {
                        total += rule.apply(pair.getLeft());
                    }
                    remaining = pair.getRight();
                }
            }
            return total;
        }

        boolean apply(Item item) {
            for (WorkflowRule rule : rules) {
                if(rule.applyCheck(item)) {
                    return rule.apply(item);
                }
            }
            throw new IllegalArgumentException();
        }

    }

    private static class WorkflowRule {
        Boolean returnValue = null;
        String redirect;
        Function<Item, Integer> getter;
        Property property;
        int value;
        Check check;

        Pair<ItemRange, ItemRange> applyCheck(ItemRange itemRange) {
            if(check.equals(Check.AUTO)) {
                return Pair.of(itemRange, null);
            }

            if(check.equals(Check.GREATER)) {
                Range range = itemRange.rangeMap.get(property);
                if(range.from <= value && range.to > value) {
                    ItemRange leftItemRange = new ItemRange(itemRange);
                    ItemRange rightItemRange = new ItemRange(itemRange);
                    leftItemRange.rangeMap.get(property).from = value + 1;
                    rightItemRange.rangeMap.get(property).to = value;
                    return Pair.of(leftItemRange, rightItemRange);
                } else if(range.from > value) {
                    return Pair.of(itemRange, null);
                } else {
                    return Pair.of(null, itemRange);
                }
            } else {
                Range range = itemRange.rangeMap.get(property);
                if(range.from < value && range.to >= value) {
                    ItemRange leftItemRange = new ItemRange(itemRange);
                    ItemRange rightItemRange = new ItemRange(itemRange);
                    leftItemRange.rangeMap.get(property).to = value - 1;
                    rightItemRange.rangeMap.get(property).from = value;
                    return Pair.of(leftItemRange, rightItemRange);
                } else if(range.to < value) {
                    return Pair.of(itemRange, null);
                } else {
                    return Pair.of(null, itemRange);
                }
            }
        }

        long apply(ItemRange itemRange) {
            if(returnValue != null) {
                if(returnValue) {
                    return itemRange.sum();
                } else {
                    return 0L;
                }
            }
            return workflowMap.get(redirect).apply(itemRange);
        }

        boolean applyCheck(Item item) {
            if(check.equals(Check.AUTO)) {
                return true;
            }
            if(check.equals(Check.GREATER)) {
                return getter.apply(item) > value;
            } else {
                return getter.apply(item) < value;
            }
        }

        boolean apply(Item item) {
            if(returnValue != null) {
                return returnValue;
            }
            return workflowMap.get(redirect).apply(item);
        }
    }

    private static class ItemRange {
        Map<Property, Range> rangeMap;

        public ItemRange(ItemRange itemRange) {
            this.rangeMap = new HashMap<>();
            for (Property property : itemRange.rangeMap.keySet()) {
                this.rangeMap.put(property, new Range(itemRange.rangeMap.get(property)));
            }
        }

        public ItemRange() {
            this.rangeMap = new HashMap<>();
            this.rangeMap.put(Property.X, new Range());
            this.rangeMap.put(Property.M, new Range());
            this.rangeMap.put(Property.A, new Range());
            this.rangeMap.put(Property.S, new Range());
        }

        long sum() {
            return (long) this.rangeMap.get(Property.X).sum() *
                        this.rangeMap.get(Property.M).sum() *
                        this.rangeMap.get(Property.A).sum() *
                        this.rangeMap.get(Property.S).sum();
        }
    }

    private static class Range {
        int from = 1;
        int to = 4000;

        int sum() {
            return to - from + 1;
        }

        public Range(Range range) {
            this.from = range.from;
            this.to = range.to;
        }

        public Range() {
        }
    }

    private static class Item {
        final int x;
        final int m;
        final int a;
        final int s;

        public Item(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        int sum() {
            return x + m + a + s;
        }

        public int getX() {
            return x;
        }

        public int getM() {
            return m;
        }

        public int getA() {
            return a;
        }

        public int getS() {
            return s;
        }
    }

    private enum Check {
        GREATER,
        SMALLER,
        AUTO;
    }

    private enum Property {
        X,
        M,
        A,
        S;
    }

}
