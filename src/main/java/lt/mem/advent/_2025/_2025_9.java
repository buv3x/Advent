package lt.mem.advent._2025;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class _2025_9 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/9.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String row : input) {
            pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(row, ",")),
                    Long.valueOf(ReaderUtil.stringAfter(row, ","))));
        }

        long max = 0L;
        for(int i = 0; i < pairs.size(); ++i) {
            for(int j = i + 1; j < pairs.size(); ++j) {
                long area = Math.abs(pairs.get(i).getLeft() - pairs.get(j).getLeft() + 1) *
                        Math.abs(pairs.get(i).getRight() - pairs.get(j).getRight() + 1);
                if(area > max) {
                    max = area;
                }
            }
        }

        System.out.println(max);

    }

    public static void second(List<String> input) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String row : input) {
            pairs.add(Pair.of(Long.valueOf(ReaderUtil.stringBefore(row, ",")),
                    Long.valueOf(ReaderUtil.stringAfter(row, ","))));
        }

        List<Boolean> turns = new ArrayList<>();
        for(int i = 0; i < pairs.size(); ++i) {
            Pair<Long, Long> prevPair = i == 0 ? pairs.get(pairs.size() - 1) : pairs.get(i - 1);
            Pair<Long, Long> nextPair = i == pairs.size() - 1 ? pairs.get(0) : pairs.get(i + 1);
            Pair<Long, Long> pair = pairs.get(i);
            if(prevPair.getLeft().equals(pair.getLeft())) {
                if(prevPair.getRight() < pair.getRight()) {
                    turns.add(nextPair.getLeft() < pair.getLeft());
                } else {
                    turns.add(nextPair.getLeft() > pair.getLeft());
                }
            } else {
                if(prevPair.getLeft() < pair.getLeft()) {
                    turns.add(nextPair.getRight() > pair.getRight());
                } else {
                    turns.add(nextPair.getRight() < pair.getRight());
                }
            }
        }

        boolean rightTurn = turns.stream().filter(s -> s).count() > turns.stream().filter(s -> !s).count();
        System.out.println(rightTurn);

//        for(int i = 0; i < pairs.size(); ++i) {
//            for (int j = i + 1; j < pairs.size(); ++j) {
//                if(Math.abs((pairs.get(i).getLeft() - pairs.get(j).getLeft())) == 1
//                        || Math.abs((pairs.get(i).getRight() - pairs.get(j).getRight())) == 1) {
//                    System.out.println("(" + i + "," + j + ") " + pairs.get(i) + " " + pairs.get(j));
//                }
//            }
//        }

//        for(int i = 0; i < pairs.size(); ++i) {
//            for (int j = i + 2; j < pairs.size(); ++j) {
//                if(Math.abs((pairs.get(i).getLeft() - pairs.get(j).getLeft())) == 0
//                        || Math.abs((pairs.get(i).getRight() - pairs.get(j).getRight())) == 0) {
//                    System.out.println("(" + i + "," + j + ") " + pairs.get(i) + " " + pairs.get(j));
//                }
//            }
//        }

        long max = process(pairs, rightTurn);
//        long max = process2(pairs, rightTurn);
        System.out.println(max);

    }

    private static long process(List<Pair<Long, Long>> pairs, boolean rightTurn) {
        long max = 0L;
        for(int i = 0; i < pairs.size(); ++i) {
            outer: for (int j = i + 1; j < pairs.size(); ++j) {
                Pair<Long, Long> point1 = pairs.get(i);
                Pair<Long, Long> point2 = pairs.get(j);
                Long minLeft = Math.min(point1.getLeft(), point2.getLeft());
                Long maxLeft = Math.max(point1.getLeft(), point2.getLeft());
                Long minRight = Math.min(point1.getRight(), point2.getRight());
                Long maxRight = Math.max(point1.getRight(), point2.getRight());

                long area = (Math.abs(point1.getLeft() - point2.getLeft()) + 1) *
                        (Math.abs(point1.getRight() - point2.getRight()) + 1);
                if(area <= max) {
                    continue;
                }

                for(int k = 0; k < pairs.size(); ++k) {
                    Pair<Long, Long> pair = pairs.get(k);
                    if(pair.getLeft() > minLeft && pair.getLeft() < maxLeft
                            && pair.getRight() > minRight && pair.getRight() < maxRight) {
                        continue outer;
                    }
                }

                for(int k = 0; k < pairs.size(); ++k) {
                    Pair<Long, Long> prevPair = k == 0 ? pairs.get(pairs.size() - 1) : pairs.get(k - 1);
                    Pair<Long, Long> pair = pairs.get(k);
                    if(prevPair.getLeft().equals(pair.getLeft())) {
                        if(Math.min(prevPair.getRight(), pair.getRight()) < maxRight &&
                                Math.max(prevPair.getRight(), pair.getRight()) > minRight &&
                                pair.getLeft() <= maxLeft && pair.getLeft() >= minLeft) {
                            if(rightTurn) {
                                if(prevPair.getRight() > pair.getRight() && pair.getLeft() > minLeft) {
                                    if(area > 1429564036) {
                                        System.out.println("Step 1: (" + i + "," + j + ") " + point1 + " " + point2 + " | " + prevPair + " " + pair + " | " + area);
                                    }
                                    continue outer;
                                }
                                if(prevPair.getRight() < pair.getRight() && pair.getLeft() < maxLeft) {
                                    if(area > 1429564036) {
                                        System.out.println("Step 2: (" + i + "," + j + ") " + point1 + " " + point2 + " | " + prevPair + " " + pair + " | " + area);
                                    }
                                    continue outer;
                                }
                            } else {
                                if(prevPair.getRight() > pair.getRight() && pair.getLeft() < maxLeft) {
//                                    System.out.println("Step 3: " + point1 + " " + point2 + " | " + prevPair + " " + pair);
                                    continue outer;
                                }
                                if(prevPair.getRight() < pair.getRight() && pair.getLeft() > minLeft) {
//                                    System.out.println("Step 4: " + point1 + " " + point2 + " | " + prevPair + " " + pair);
                                    continue outer;
                                }
                            }
                        }
                    } else {
                        if(Math.min(prevPair.getLeft(), pair.getLeft()) < maxLeft &&
                                Math.max(prevPair.getLeft(), pair.getLeft()) > minLeft &&
                                pair.getRight() <= maxRight && pair.getRight() >= minRight) {
                            if(rightTurn) {
                                if(prevPair.getLeft() > pair.getLeft() && pair.getRight() < maxRight) {
                                    if(area > 1429564036) {
                                        System.out.println("Step 5: (" + i + "," + j + ") " + point1 + " " + point2 + " | " + prevPair + " " + pair + " | " + area);
                                    }
                                    continue outer;
                                }
                                if(prevPair.getLeft() < pair.getLeft() && pair.getRight() > minRight) {
                                    if(area > 1429564036) {
                                        System.out.println("Step 6: (" + i + "," + j + ") " + point1 + " " + point2 + " | " + prevPair + " " + pair + " | " + area);
                                    }
                                    continue outer;
                                }
                            } else {
                                if(prevPair.getLeft() > pair.getLeft() && pair.getRight() > minRight) {
//                                    System.out.println("Step 7: " + point1 + " " + point2 + " | " + prevPair + " " + pair);
                                    continue outer;
                                }
                                if(prevPair.getLeft() < pair.getLeft() && pair.getRight() < maxRight) {
//                                    System.out.println("Step 8: " + point1 + " " + point2 + " | " + prevPair + " " + pair);
                                    continue outer;
                                }
                            }
                        }
                    }
                }

                System.out.println(point1 + " " + point2 + " " + area);
                max = area;
            }
        }
        return max;
    }

    private static long process2(List<Pair<Long, Long>> pairs, boolean rightTurn) {
        long max = 0L;
        for(int i = 0; i < pairs.size(); ++i) {
            outer: for (int j = i + 1; j < pairs.size(); ++j) {
                Pair<Long, Long> point1 = pairs.get(i);
                Pair<Long, Long> point2 = pairs.get(j);
                if(point1.getRight() <= 48476 && point2.getRight() <= 48476) {
                    Long minLeft = Math.min(point1.getLeft(), point2.getLeft());
                    Long maxLeft = Math.max(point1.getLeft(), point2.getLeft());
                    Long minRight = Math.min(point1.getRight(), point2.getRight());
                    Long maxRight = Math.max(point1.getRight(), point2.getRight());

                    long area = (Math.abs(point1.getLeft() - point2.getLeft()) + 1) *
                            (Math.abs(point1.getRight() - point2.getRight()) + 1);
                    if (area <= max) {
                        continue;
                    }

                    for (int k = 0; k < pairs.size(); ++k) {
                        Pair<Long, Long> pair = pairs.get(k);
                        if (pair.getLeft() > minLeft && pair.getLeft() < maxLeft
                                && pair.getRight() > minRight && pair.getRight() < maxRight) {
                            continue outer;
                        }
                    }
                    System.out.println(point1 + " " + point2 + " " + area);
                    max = area;
                }
            }
        }

        return max;

    }


}
