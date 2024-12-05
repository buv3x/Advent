package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.List;
import java.util.Map;

public class _2015_21 {

    private static final int START_HEALTH = 100;

    private static final Map<Integer, Integer> damage = Map.ofEntries(
            Map.entry(8, 4),
            Map.entry(10,5),
            Map.entry(25,6),
            Map.entry(40, 7),
            Map.entry(74,8)
    );

    private static final Map<Integer, Integer> armor = Map.ofEntries(
            Map.entry(13, 1),
            Map.entry(31,2),
            Map.entry(53,3),
            Map.entry(75, 4),
            Map.entry(102,5)
    );

    private static final Map<Integer, Integer> damageRings = Map.ofEntries(
            Map.entry(25, 1),
            Map.entry(50,2),
            Map.entry(100,3)
    );

    private static final Map<Integer, Integer> armorRings = Map.ofEntries(
            Map.entry(20, 1),
            Map.entry(40,2),
            Map.entry(80,3)
    );

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/21.txt");
//        first(lineInput);
        second(lineInput);
    }

    private static int minCost = Integer.MAX_VALUE;

    private static int bossHealth = Integer.MAX_VALUE;
    private static int bossDamage = Integer.MAX_VALUE;
    private static int bossArmor = Integer.MAX_VALUE;

    public static void first(List<String> lineInput) {
        bossHealth = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(0), ": "));
        bossDamage = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(1), ": "));
        bossArmor = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(2), ": "));

        selectWeapon();

        System.out.println(minCost);
    }

    private static void selectWeapon() {
        for (Integer weaponCost : damage.keySet()) {
            selectArmor(weaponCost, damage.get(weaponCost));
        }
    }

    private static void selectArmor(int cost, int damage) {
        if(cost >= minCost) {
            return;
        }
        selectFirstRing(cost, damage, 0);
        for (Integer armorCost : armor.keySet()) {
            selectFirstRing(cost + armorCost, damage, armor.get(armorCost));
        }
    }

    private static void selectFirstRing(int cost, int damage, int armor) {
        if(cost >= minCost) {
            return;
        }
        calculateResult(cost, damage, armor);
        for (Integer ringCost : damageRings.keySet()) {
            selectSecondRing(cost + ringCost, damage + damageRings.get(ringCost), armor, true, ringCost);
        }
        for (Integer ringCost : armorRings.keySet()) {
            selectSecondRing(cost + ringCost, damage, armor + armorRings.get(ringCost), false, ringCost);
        }
    }

    private static void selectSecondRing(int cost, int damage, int armor, boolean firstWeapon, int firstCost) {
        if(cost >= minCost) {
            return;
        }
        calculateResult(cost, damage, armor);
        for (Integer ringCost : damageRings.keySet()) {
            if(!firstWeapon || !ringCost.equals(firstCost)) {
                calculateResult(cost + ringCost, damage + damageRings.get(ringCost), armor);
            }
        }
        for (Integer ringCost : armorRings.keySet()) {
            if(firstWeapon || !ringCost.equals(firstCost)) {
                calculateResult(cost + ringCost, damage, armor + armorRings.get(ringCost));
            }
        }
    }

    private static void calculateResult(int cost, int damage, int armor) {
        if(cost >= minCost) {
            return;
        }
        if(calculate(START_HEALTH, damage, armor)) {
            minCost = cost;
        }
    }

    private static boolean calculate(int health, int damage, int armor) {
        int moves = Double.valueOf(Math.ceil(((double) bossHealth) / Math.max(1, damage - bossArmor))).intValue();
        int bossMoves = Double.valueOf(Math.ceil(((double) health) / Math.max(1, bossDamage - armor))).intValue();

        return moves <= bossMoves;
    }

    private static int maxCost = 0;

    public static void second(List<String> lineInput) {
        bossHealth = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(0), ": "));
        bossDamage = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(1), ": "));
        bossArmor = Integer.parseInt(ReaderUtil.stringAfter(lineInput.get(2), ": "));

        selectWeapon2();
        System.out.println(maxCost);
    }

    private static void selectWeapon2() {
        for (Integer weaponCost : damage.keySet()) {
            selectArmor2(weaponCost, damage.get(weaponCost));
        }
    }

    private static void selectArmor2(int cost, int damage) {
        selectFirstRing2(cost, damage, 0);
        for (Integer armorCost : armor.keySet()) {
            selectFirstRing2(cost + armorCost, damage, armor.get(armorCost));
        }
    }

    private static void selectFirstRing2(int cost, int damage, int armor) {
        calculateResult2(cost, damage, armor);
        for (Integer ringCost : damageRings.keySet()) {
            selectSecondRing2(cost + ringCost, damage + damageRings.get(ringCost), armor, true, ringCost);
        }
        for (Integer ringCost : armorRings.keySet()) {
            selectSecondRing2(cost + ringCost, damage, armor + armorRings.get(ringCost), false, ringCost);
        }
    }

    private static void selectSecondRing2(int cost, int damage, int armor, boolean firstWeapon, int firstCost) {
        calculateResult2(cost, damage, armor);
        for (Integer ringCost : damageRings.keySet()) {
            if(!firstWeapon || !ringCost.equals(firstCost)) {
                calculateResult2(cost + ringCost, damage + damageRings.get(ringCost), armor);
            }
        }
        for (Integer ringCost : armorRings.keySet()) {
            if(firstWeapon || !ringCost.equals(firstCost)) {
                calculateResult2(cost + ringCost, damage, armor + armorRings.get(ringCost));
            }
        }
    }

    private static void calculateResult2(int cost, int damage, int armor) {
        if(calculate2(START_HEALTH, damage, armor)) {
            if(cost > maxCost) {
                maxCost = cost;
            }
        }
    }

    private static boolean calculate2(int health, int damage, int armor) {
        int moves = Double.valueOf(Math.ceil(((double) bossHealth) / Math.max(1, damage - bossArmor))).intValue();
        int bossMoves = Double.valueOf(Math.ceil(((double) health) / Math.max(1, bossDamage - armor))).intValue();

        return moves > bossMoves;
    }


}
