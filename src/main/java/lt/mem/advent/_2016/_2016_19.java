package lt.mem.advent._2016;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class _2016_19 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/19.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        int value = Integer.parseInt(lineInput.get(0));
        int power = 0;
        while (2 << power < value) {
            power++;
        }
        System.out.println(power);

        int remainder = value - (2 << (power - 1));
        System.out.println(remainder);

        System.out.println(1 + remainder * 2);

    }

    public static void second(List<String> lineInput) {
        int value = Integer.parseInt(lineInput.get(0));
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= value; ++i) {
            list.add(i);
        }

        System.out.println("List created: " + list.size());

        int index = 0;
        while (list.size() > 1) {
            if(list.size() % 1000 == 0) {
                System.out.println("List size: " + list.size());
            }
            int toDelete = (index + (list.size() / 2)) % list.size();
            list.remove(toDelete);
            if(toDelete > index) {
                ++index;
            }
            index = index % list.size();
        }

        System.out.println(list.get(0));

    }

}
