package lt.mem.advent._2023;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2023_14 {

    private final static int CYCLE_COUNT = 1000;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/14.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        List<List<Stone>> stones = new ArrayList<>();
        for(int i = 0; i < input.get(0).length(); ++i) {
            ArrayList<Stone> stonesList = new ArrayList<>();
            stones.add(stonesList);
            for(int j = 0; j < input.size(); ++j) {
                char c = input.get(j).charAt(i);
                if(c != '.') {
                    stonesList.add(new Stone(c == 'O', j));
                }
            }
        }

        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); ++i) {
                Stone stone = stoneList.get(i);
                if(stone.moveable) {
                    if(i == 0) {
                        stone.coordinate = 0;
                    } else {
                        stone.coordinate = stoneList.get(i - 1).coordinate + 1;
                    }
                }
            }
        }

        int size = input.size();
        long total = 0;
        for (List<Stone> stoneList : stones) {
            for (Stone stone : stoneList) {
                if(stone.moveable) {
                    total += (size - stone.coordinate);
                }
            }
        }
        System.out.println(total);

    }


    public static void second(List<String> input) {
        Map<Point2D, Stone2> stones = new HashMap<>();
        int width = input.get(0).length();
        int height = input.size();
        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                char c = input.get(i).charAt(j);
                if(c != '.') {
                    stones.put(new Point2D(j, i), new Stone2(c == 'O', j, i));
                }
            }
        }

        Map<String, Integer> stoneStringSet = new HashMap<>();
        int cycleLength = 0;
        int firstHit = 0;
        for(int i = 1; i <= CYCLE_COUNT; ++i) {
            stones = cycle(stones, width, height);
            String stoneString = printStones(stones, width, height);
            if(stoneStringSet.containsKey(stoneString)) {
                firstHit = i;
                cycleLength = i - stoneStringSet.get(stoneString);
                break;
            } else {
                stoneStringSet.put(stoneString, i);
            }
        }
        System.out.println(firstHit);
        System.out.println(cycleLength);
        int skips = (CYCLE_COUNT - firstHit) / cycleLength;
        System.out.println(skips);
        int nextHit = firstHit + (skips * cycleLength) + 1;
        System.out.println(nextHit);
        for(int i = nextHit; i <= CYCLE_COUNT; ++i) {
            stones = cycle(stones, width, height);
        }

        long total = calculateValue(stones, height);
        System.out.println(total);
    }

    private static long calculateValue(Map<Point2D, Stone2> stones, int height) {
        long total = 0;
        for (Stone2 stone : stones.values()) {
            if(stone.moveable) {
                total += (height - stone.y);
            }
        }
        return total;
    }

    private static Map<Point2D, Stone2> cycle(Map<Point2D, Stone2> stones, int width, int height) {
        List<List<Stone2>> stonesToMove = prepareNorthMove(stones, width, height);
        moveNorth(stonesToMove);
        stones = gatherStones(stonesToMove);
//        printStones(width, height, stones);
        stonesToMove = prepareWestMove(stones, width, height);
        moveWest(stonesToMove);
        stones = gatherStones(stonesToMove);
//        printStones(width, height, stones);
        stonesToMove = prepareSouthMove(stones, width, height);
        moveSouth(stonesToMove, height);
        stones = gatherStones(stonesToMove);
//        printStones(width, height, stones);
        stonesToMove = prepareEastMove(stones, width, height);
        moveEast(stonesToMove, width);
        stones = gatherStones(stonesToMove);
        return stones;
    }

    private static Map<Point2D, Stone2> gatherStones(List<List<Stone2>> stonesToMove) {
        Map<Point2D, Stone2> stones = new HashMap<>();
        for (List<Stone2> stonesList : stonesToMove) {
            for (Stone2 stone : stonesList) {
                stones.put(new Point2D(stone.x, stone.y), stone);
            }
        }
        return stones;
    }

    private static List<List<Stone2>> prepareNorthMove(Map<Point2D, Stone2> stones, int width, int height) {
        List<List<Stone2>> stonesToMove = new ArrayList<>();
        for(int i = 0; i < width; ++i) {
            ArrayList<Stone2> stonesList = new ArrayList<>();
            stonesToMove.add(stonesList);
            for(int j = 0; j < height; ++j) {
                Point2D key = new Point2D(i, j);
                if(stones.containsKey(key)) {
                    stonesList.add(stones.get(key));
                }
            }
        }
        return stonesToMove;
    }

    private static List<List<Stone2>> prepareWestMove(Map<Point2D, Stone2> stones, int width, int height) {
        List<List<Stone2>> stonesToMove = new ArrayList<>();
        for(int i = 0; i < height; ++i) {
            ArrayList<Stone2> stonesList = new ArrayList<>();
            stonesToMove.add(stonesList);
            for(int j = 0; j < width; ++j) {
                Point2D key = new Point2D(j, i);
                if(stones.containsKey(key)) {
                    stonesList.add(stones.get(key));
                }
            }
        }
        return stonesToMove;
    }

    private static List<List<Stone2>> prepareSouthMove(Map<Point2D, Stone2> stones, int width, int height) {
        List<List<Stone2>> stonesToMove = new ArrayList<>();
        for(int i = 0; i < width; ++i) {
            ArrayList<Stone2> stonesList = new ArrayList<>();
            stonesToMove.add(stonesList);
            for(int j = height - 1; j >= 0; --j) {
                Point2D key = new Point2D(i, j);
                if(stones.containsKey(key)) {
                    stonesList.add(stones.get(key));
                }
            }
        }
        return stonesToMove;
    }

    private static List<List<Stone2>> prepareEastMove(Map<Point2D, Stone2> stones, int width, int height) {
        List<List<Stone2>> stonesToMove = new ArrayList<>();
        for(int i = 0; i < height; ++i) {
            ArrayList<Stone2> stonesList = new ArrayList<>();
            stonesToMove.add(stonesList);
            for(int j = width; j >= 0; --j) {
                Point2D key = new Point2D(j, i);
                if(stones.containsKey(key)) {
                    stonesList.add(stones.get(key));
                }
            }
        }
        return stonesToMove;
    }

    private static String printStones(Map<Point2D, Stone2> stones, int width, int height) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Point2D key = new Point2D(j, i);
                if(stones.containsKey(key)) {
                    sb.append(stones.get(key).moveable ? 'O' : '#');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static void moveNorth(List<List<Stone2>> stones) {
        for (List<Stone2> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); ++i) {
                Stone2 stone = stoneList.get(i);
                if(stone.moveable) {
                    if(i == 0) {
                        stone.y = 0;
                    } else {
                        stone.y = stoneList.get(i - 1).y + 1;
                    }
                }
            }
        }
    }

    private static void moveWest(List<List<Stone2>> stones) {
        for (List<Stone2> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); ++i) {
                Stone2 stone = stoneList.get(i);
                if(stone.moveable) {
                    if(i == 0) {
                        stone.x = 0;
                    } else {
                        stone.x = stoneList.get(i - 1).x + 1;
                    }
                }
            }
        }
    }

    private static void moveSouth(List<List<Stone2>> stones, int height) {
        for (List<Stone2> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); ++i) {
                Stone2 stone = stoneList.get(i);
                if(stone.moveable) {
                    if(i == 0) {
                        stone.y = height - 1;
                    } else {
                        stone.y = stoneList.get(i - 1).y - 1;
                    }
                }
            }
        }
    }

    private static void moveEast(List<List<Stone2>> stones, int width) {
        for (List<Stone2> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); ++i) {
                Stone2 stone = stoneList.get(i);
                if(stone.moveable) {
                    if(i == 0) {
                        stone.x = width - 1;
                    } else {
                        stone.x = stoneList.get(i - 1).x - 1;
                    }
                }
            }
        }
    }

    private static class Stone2 {

        final boolean moveable;
        int x;

        int y;

        public Stone2(boolean moveable, int x, int y) {
            this.moveable = moveable;
            this.x = x;
            this.y = y;
        }
    }

    private static class Stone {

        final boolean moveable;
        int coordinate;

        public Stone(boolean moveable, int coordinate) {
            this.moveable = moveable;
            this.coordinate = coordinate;
        }
    }


}
