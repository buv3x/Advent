package lt.mem.advent.util;

public class MathUtil {

    /**
     * Calculate Lowest Common Multiplier
     */
    public static long LCM(long a, long b) {
        return (a * b) / GCF(a, b);
    }

    /**
     * Calculate Greatest Common Factor
     */
    public static long GCF(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (GCF(b, a % b));
        }
    }

}
