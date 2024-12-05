package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class _2015_15 {

    private static int CALORIES_COUNT = 500;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/15.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        calculate(lineInput, false);
    }

    private static void calculate(List<String> lineInput, boolean checkCalories) {
        List<Recipe> recipes = new ArrayList<>();
        for (String line : lineInput) {
            int capacity = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "capacity "), ","));
            int durability = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "durability "), ","));
            int flavor = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "flavor "), ","));
            int texture = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "texture "), ","));
            int calories = Integer.parseInt(ReaderUtil.stringAfter(line, "calories "));
            recipes.add(new Recipe(capacity, durability, flavor, texture, calories));
        }

        int maxCapacity = recipes.stream().map(r -> r.capacity).max(Integer::compareTo).get();
        int maxDurability = recipes.stream().map(r -> r.durability).max(Integer::compareTo).get();
        int maxFlavour = recipes.stream().map(r -> r.flavour).max(Integer::compareTo).get();
        int maxTexture = recipes.stream().map(r -> r.texture).max(Integer::compareTo).get();

        List<Integer> maxValues = new ArrayList<>();

        for (Recipe recipe : recipes) {
            int maxValue = 100;
            if(recipe.capacity == 0 || recipe.durability == 0 || recipe.flavour == 0 || recipe.texture == 0) {
                maxValue = 99;
            }
            if(recipe.capacity < 0) {
                int value = (100 * maxCapacity) / (maxCapacity - recipe.capacity);
                if(value < maxValue) {
                    maxValue = value;
                }
            }
            if(recipe.durability < 0) {
                int value = (100 * maxDurability) / (maxDurability - recipe.durability);
                if(value < maxValue) {
                    maxValue = value;
                }
            }
            if(recipe.flavour < 0) {
                int value = (100 * maxFlavour) / (maxFlavour - recipe.flavour);
                if(value < maxValue) {
                    maxValue = value;
                }
            }
            if(recipe.texture < 0) {
                int value = (100 * maxTexture) / (maxTexture - recipe.texture);
                if(value < maxValue) {
                    maxValue = value;
                }
            }
            maxValues.add(maxValue);
        }

        int total = calculateList(new ArrayList<>(), recipes, maxValues, checkCalories);

        System.out.println(total);
    }

    private static int calculateList(List<Integer> values, List<Recipe> recipes, List<Integer> maxValues, boolean checkCalories) {
        if(values.size() == recipes.size()) {
            int capacity = 0;
            int durability = 0;
            int flavour = 0;
            int texture = 0;
            int calories = 0;
            for(int i = 0; i < values.size(); ++i) {
                capacity += values.get(i) * recipes.get(i).capacity;
                durability += values.get(i) * recipes.get(i).durability;
                flavour += values.get(i) * recipes.get(i).flavour;
                texture += values.get(i) * recipes.get(i).texture;
                calories += values.get(i) * recipes.get(i).calories;
            }
            if(!checkCalories || calories == CALORIES_COUNT) {
                return Math.max(capacity, 0) * Math.max(durability, 0) * Math.max(flavour, 0) * Math.max(texture, 0);
            } else {
                return 0;
            }
        } else {
            if(values.size() == recipes.size() - 1) {
                List<Integer> newValues = new ArrayList<>(values);
                int sum = 0;
                for (Integer value : values) {
                    sum += value;
                }
                newValues.add(100 - sum);
                return calculateList(newValues, recipes, maxValues, checkCalories);
            } else {
                int maxValue = maxValues.get(values.size());
                int maxTotal = 0;
                for (int i = 0; i <= maxValue; ++i) {
                    List<Integer> newValues = new ArrayList<>(values);
                    newValues.add(i);
                    int total = calculateList(newValues, recipes, maxValues, checkCalories);
                    if(total > maxTotal) {
                        maxTotal = total;
                    }
                }
                return maxTotal;
            }
        }
    }

    public static void second(List<String> lineInput) {
        calculate(lineInput, true);
    }

    private static class Recipe {
        int capacity;
        int durability;
        int flavour;
        int texture;
        int calories;
        public Recipe(int capacity, int durability, int flavour, int texture, int calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavour = flavour;
            this.texture = texture;
            this.calories = calories;
        }
    }

}
