package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class _2016_21 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/21.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static String INITIAL = "gdfcabeh";
    private final static String FINAL = "fbgdceah";

    public static void first(List<String> lineInput) {
        List<Operation> operations = parse(lineInput);

        String password = INITIAL;
        System.out.println(password);
        for (Operation operation : operations) {
            password = apply(operation, password);
            System.out.println(operation.original + ": " + password);
        }

        System.out.println(password);

    }

    private static List<Operation> parse(List<String> lineInput) {
        List<Operation> operations = new ArrayList<>();
        for (String row : lineInput) {
            Operation operation = new Operation();
            operation.original = row;
            if(row.startsWith("swap position ")) {
                operation.action = Action.SWAP_POSITION;
                operation.firstValue = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "swap position "), " with position"));
                operation.secondValue = Integer.parseInt(ReaderUtil.stringAfter(row, "with position "));
            } else if(row.startsWith("swap letter ")) {
                operation.action = Action.SWAP_LETTER;
                operation.firstLetter = ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "swap letter "), " with letter").charAt(0);
                operation.secondLetter = ReaderUtil.stringAfter(row, "with letter ").charAt(0);
            } else if(row.startsWith("reverse positions ")) {
                operation.action = Action.REVERSE;
                operation.firstValue = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "reverse positions "), " through"));
                operation.secondValue = Integer.parseInt(ReaderUtil.stringAfter(row, "through "));
            } else if(row.startsWith("move position ")) {
                operation.action = Action.MOVE;
                operation.firstValue = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "move position "), " to position"));
                operation.secondValue = Integer.parseInt(ReaderUtil.stringAfter(row, "to position "));
            } else if(row.startsWith("rotate based on position of letter ")) {
                operation.action = Action.ROTATE;
                operation.firstLetter = ReaderUtil.stringAfter(row, "rotate based on position of letter ").charAt(0);
            } else if(row.startsWith("rotate left ")) {
                operation.action = Action.ROTATE_LEFT;
                operation.firstValue = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "rotate left "), " step"));
            } else {
                operation.action = Action.ROTATE_RIGHT;
                operation.firstValue = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "rotate right "), " step"));
            }

            operations.add(operation);
        }
        return operations;
    }

    private static String apply(Operation operation, String password) {
        switch (operation.action) {
            case MOVE: {
                char c = password.charAt(operation.firstValue);
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i != operation.firstValue) {
                        sb.append(password.charAt(i));
                    }
                }
                String temp = sb.toString();
                sb = new StringBuilder();
                for(int i = 0; i < temp.length(); ++i) {
                    if(i == operation.secondValue) {
                        sb.append(c);
                    }
                    sb.append(temp.charAt(i));
                }
                if(temp.length() == operation.secondValue) {
                    sb.append(c);
                }
                return sb.toString();
            }
            case REVERSE: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i >= operation.firstValue && i <= operation.secondValue) {
                        sb.append(password.charAt(operation.firstValue + operation.secondValue - i));
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
            case ROTATE_LEFT: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i+ operation.firstValue) % password.length()));
                }
                return sb.toString();
            }
            case ROTATE_RIGHT: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i + password.length() - operation.firstValue) % password.length()));
                }
                return sb.toString();
            }
            case ROTATE: {
                int index = password.indexOf(operation.firstLetter);
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i + (password.length() * 2) - index - 1 - (index >= 4 ? 1 : 0)) % password.length()));
                }
                return sb.toString();
            }
            case SWAP_POSITION: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i == operation.firstValue) {
                        sb.append(password.charAt(operation.secondValue));
                    } else if(i == operation.secondValue) {
                        sb.append(password.charAt(operation.firstValue));
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
            case SWAP_LETTER: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(password.charAt(i) == operation.firstLetter) {
                        sb.append(operation.secondLetter);
                    } else if(password.charAt(i) == operation.secondLetter) {
                        sb.append(operation.firstLetter);
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
        }
        return password;
    }


    public static void second(List<String> lineInput) {
        List<Operation> operations = parse(lineInput);
        Collections.reverse(operations);

        String password = FINAL;
        System.out.println(password);
        for (Operation operation : operations) {
            password = applyReverse(operation, password);
            System.out.println(operation.original + ": " + password);
        }

        System.out.println(password);
    }

    private static final Map<Integer, Integer> reverseRotate = Map.ofEntries(Map.entry(0, 7), Map.entry(1, 7),
            Map.entry(2, 2), Map.entry(3, 6), Map.entry(4, 1), Map.entry(5, 5),
            Map.entry(6, 0), Map.entry(7, 4));

    private static String applyReverse(Operation operation, String password) {
        switch (operation.action) {
            case MOVE: {
                char c = password.charAt(operation.secondValue);
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i != operation.secondValue) {
                        sb.append(password.charAt(i));
                    }
                }
                String temp = sb.toString();
                sb = new StringBuilder();
                for(int i = 0; i < temp.length(); ++i) {
                    if(i == operation.firstValue) {
                        sb.append(c);
                    }
                    sb.append(temp.charAt(i));
                }
                if(temp.length() == operation.firstValue) {
                    sb.append(c);
                }
                return sb.toString();
            }
            case REVERSE: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i >= operation.firstValue && i <= operation.secondValue) {
                        sb.append(password.charAt(operation.firstValue + operation.secondValue - i));
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
            case ROTATE_LEFT: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i + password.length() - operation.firstValue) % password.length()));
                }
                return sb.toString();
            }
            case ROTATE_RIGHT: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i + operation.firstValue) % password.length()));
                }
                return sb.toString();
            }
            case ROTATE: {
                int index = password.indexOf(operation.firstLetter);
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    sb.append(password.charAt((i + password.length() - reverseRotate.get(index)) % password.length()));
                }
                return sb.toString();
            }
            case SWAP_POSITION: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(i == operation.firstValue) {
                        sb.append(password.charAt(operation.secondValue));
                    } else if(i == operation.secondValue) {
                        sb.append(password.charAt(operation.firstValue));
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
            case SWAP_LETTER: {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < password.length(); ++i) {
                    if(password.charAt(i) == operation.firstLetter) {
                        sb.append(operation.secondLetter);
                    } else if(password.charAt(i) == operation.secondLetter) {
                        sb.append(operation.firstLetter);
                    } else {
                        sb.append(password.charAt(i));
                    }
                }
                return sb.toString();
            }
        }
        return password;
    }

    private static class Operation {
        Action action;
        char firstLetter;
        char secondLetter;
        int firstValue;
        int secondValue;
        String original;
    }

    private enum Action {
        SWAP_POSITION,
        SWAP_LETTER,
        ROTATE_LEFT,
        ROTATE_RIGHT,
        ROTATE,
        REVERSE,
        MOVE
    }

}
