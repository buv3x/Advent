package lt.mem.advent._2016;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class _2016_3 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/3.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        int total = 0;
        for (String row : lineInput) {
            String[] s = StringUtils.split(row, " ");
            int a = Integer.parseInt(StringUtils.trim(s[0]));
            int b = Integer.parseInt(StringUtils.trim(s[1]));
            int c = Integer.parseInt(StringUtils.trim(s[2]));

            int max = Math.max(a, Math.max(b, c));

            if(a + b + c > max * 2) {
                ++total;
            }
        }
        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        int total = 0;
        for (int i = 0; i < lineInput.size() / 3; ++i) {
            String row1 = lineInput.get(i * 3);
            String row2 = lineInput.get(i * 3 + 1);
            String row3 = lineInput.get(i * 3 + 2);

            String[] s1 = StringUtils.split(row1, " ");
            String[] s2 = StringUtils.split(row2, " ");
            String[] s3 = StringUtils.split(row3, " ");
            int a1 = Integer.parseInt(StringUtils.trim(s1[0]));
            int b1 = Integer.parseInt(StringUtils.trim(s2[0]));
            int c1 = Integer.parseInt(StringUtils.trim(s3[0]));

            int a2 = Integer.parseInt(StringUtils.trim(s1[1]));
            int b2 = Integer.parseInt(StringUtils.trim(s2[1]));
            int c2 = Integer.parseInt(StringUtils.trim(s3[1]));

            int a3 = Integer.parseInt(StringUtils.trim(s1[2]));
            int b3 = Integer.parseInt(StringUtils.trim(s2[2]));
            int c3 = Integer.parseInt(StringUtils.trim(s3[2]));

            int max1 = Math.max(a1, Math.max(b1, c1));
            int max2 = Math.max(a2, Math.max(b2, c2));
            int max3 = Math.max(a3, Math.max(b3, c3));

            if(a1 + b1 + c1 > max1 * 2) {
                ++total;
            }
            if(a2 + b2 + c2 > max2 * 2) {
                ++total;
            }
            if(a3 + b3 + c3 > max3 * 2) {
                ++total;
            }
        }
        System.out.println(total);
    }




}
