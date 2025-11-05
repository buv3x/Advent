package lt.mem.advent._2016;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class _2016_18 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/18.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int ROWS = 400000;

    public static void first(List<String> lineInput) {
        List<String> rows = new ArrayList<>();
        rows.add(lineInput.get(0));
        for(int i = 0; i < ROWS - 1; ++i) {
            String row = rows.get(i);
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < row.length(); ++j) {
                boolean left = j > 0 && row.charAt(j - 1) == '^';
                boolean center = row.charAt(j) == '^';
                boolean right = j < row.length() - 1 && row.charAt(j + 1) == '^';

                boolean trap = (left && center && !right) || (!left && center && right)
                        || (left && !center && !right) || (!left && !center && right);
                sb.append(trap ? '^' : '.');

            }
            rows.add(sb.toString());
        }

        int count = 0;
        for (String row : rows) {
            count += StringUtils.countMatches(row, '.');
            System.out.println(row);
        }
        System.out.println(count);
    }

    public static void second(List<String> lineInput) {
        List<String> rows = new ArrayList<>();
        rows.add(lineInput.get(0));
        for(int i = 0; i < ROWS - 1; ++i) {
            String row = rows.get(i);
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < row.length(); ++j) {
                boolean left = j > 0 && row.charAt(j - 1) == '^';
                boolean center = row.charAt(j) == '^';
                boolean right = j < row.length() - 1 && row.charAt(j + 1) == '^';

                boolean trap = (left && center && !right) || (!left && center && right)
                        || (left && !center && !right) || (!left && !center && right);
                sb.append(trap ? '^' : '.');

            }
            rows.add(sb.toString());
        }

        int count = 0;
        for (String row : rows) {
            count += StringUtils.countMatches(row, '.');
//            System.out.println(row);
        }
        System.out.println(count);
    }

}
