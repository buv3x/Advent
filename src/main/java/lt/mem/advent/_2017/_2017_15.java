package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class _2017_15 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/15.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        long aSeed = Long.parseLong(ReaderUtil.stringAfter(lineInput.get(0), " starts with "));
        long bSeed = Long.parseLong(ReaderUtil.stringAfter(lineInput.get(1), " starts with "));

        int total = 0;
        for(int i = 0; i < 40000000; ++i) {
            long aValue = (aSeed * 16807) % 2147483647;
            long bValue = (bSeed * 48271) % 2147483647;

            String aBin = StringUtils.leftPad(Long.toBinaryString(aValue), 32, '0');
            String bBin = StringUtils.leftPad(Long.toBinaryString(bValue), 32, '0');
            if(aBin.substring(16).equals(bBin.substring(16))) {
                total++;
            }

            aSeed = aValue;
            bSeed = bValue;
        }

        System.out.println(total);
    }

    public static void second(List<String> lineInput) {
        long aSeed = Long.parseLong(ReaderUtil.stringAfter(lineInput.get(0), " starts with "));
        long bSeed = Long.parseLong(ReaderUtil.stringAfter(lineInput.get(1), " starts with "));
        int total = 0;
        for(int i = 0; i < 5000000; ++i) {
            long aValue = (aSeed * 16807) % 2147483647;
            while(aValue % 4 != 0) {
                aSeed = aValue;
                aValue = (aSeed * 16807) % 2147483647;
            }

            long bValue = (bSeed * 48271) % 2147483647;
            while(bValue % 8 != 0) {
                bSeed = bValue;
                bValue = (bSeed * 48271) % 2147483647;
            }

            String aBin = StringUtils.leftPad(Long.toBinaryString(aValue), 32, '0');
            String bBin = StringUtils.leftPad(Long.toBinaryString(bValue), 32, '0');
            if(aBin.substring(16).equals(bBin.substring(16))) {
                total++;
            }


            aSeed = aValue;
            bSeed = bValue;
        }
        System.out.println(total);
    }

}
