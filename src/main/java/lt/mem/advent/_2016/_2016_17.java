package lt.mem.advent._2016;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class _2016_17 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/17.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        String salt = lineInput.get(0);
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(3, 3);

        List<State> states = new ArrayList<>();
        State initial = new State();
        initial.point = start;
        initial.path = "";
        states.add(initial);
        while(true) {
            List<State> newStates = new ArrayList<>();
            for (State state : states) {
                String hex = DigestUtils.md5Hex(salt + state.path);
                if(hex.charAt(0) >= 'b') {
                    if (extracted(state, Direction.UP, "U", end, newStates)) return;
                }
                if(hex.charAt(1) >= 'b') {
                    if (extracted(state, Direction.DOWN, "D", end, newStates)) return;
                }
                if(hex.charAt(2) >= 'b') {
                    if (extracted(state, Direction.LEFT, "L", end, newStates)) return;
                }
                if(hex.charAt(3) >= 'b') {
                    if (extracted(state, Direction.RIGHT, "R", end, newStates)) return;
                }
            }
            states = newStates;
        }
    }

    private static boolean extracted(State state, Direction up, String U, Point2D end, List<State> newStates) {
        Point2D newPoint = PointsUtil.newPoint(state.point, up, 0, 0, 3, 3);
        if (newPoint != null) {
            State newState = new State();
            newState.point = newPoint;
            newState.path = state.path + U;
            if (newPoint.equals(end)) {
                System.out.println(newState.path);
                return true;
            }
            newStates.add(newState);
        }
        return false;
    }

    private static int extracted2(State state, Direction up, String U, Point2D end, List<State> newStates) {
        Point2D newPoint = PointsUtil.newPoint(state.point, up, 0, 0, 3, 3);
        if (newPoint != null) {
            State newState = new State();
            newState.point = newPoint;
            newState.path = state.path + U;
            if (newPoint.equals(end)) {
//                System.out.println(newState.path);
                return newState.path.length();
            }
            newStates.add(newState);
        }
        return -1;
    }

    public static void second(List<String> lineInput) {
        String salt = lineInput.get(0);
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(3, 3);
        int maxPath = 0;
        List<State> states = new ArrayList<>();
        State initial = new State();
        initial.point = start;
        initial.path = "";
        states.add(initial);
        while(CollectionUtils.isNotEmpty(states)) {
            List<State> newStates = new ArrayList<>();
            for (State state : states) {
                String hex = DigestUtils.md5Hex(salt + state.path);
                if(hex.charAt(0) >= 'b') {
                    int length = extracted2(state, Direction.UP, "U", end, newStates);
                    if (length > maxPath) {
                        maxPath = length;
                    }
                }
                if(hex.charAt(1) >= 'b') {
                    int length = extracted2(state, Direction.DOWN, "D", end, newStates);
                    if (length > maxPath) {
                        maxPath = length;
                    }
                }
                if(hex.charAt(2) >= 'b') {
                    int length = extracted2(state, Direction.LEFT, "L", end, newStates);
                    if (length > maxPath) {
                        maxPath = length;
                    }
                }
                if(hex.charAt(3) >= 'b') {
                    int length = extracted2(state, Direction.RIGHT, "R", end, newStates);
                    if (length > maxPath) {
                        maxPath = length;
                    }
                }
            }
            states = newStates;
        }

        System.out.println(maxPath);
    }

    private static class State {

        Point2D point;
        String path;

    }

}
