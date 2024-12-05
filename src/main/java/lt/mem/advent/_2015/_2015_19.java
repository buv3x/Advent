package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2015_19 {

    private static final Map<String, List<String>> formulas = new HashMap<>();

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/19_md.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        String molecule = fillFormulas(lineInput);

        Set<String> results = transform(molecule);

        System.out.println(results);
        System.out.println(results.size());

    }

    private static Set<String> transform(String molecule) {
        Set<String> results = new HashSet<>();
        for (Map.Entry<String, List<String>> formulaEntry : formulas.entrySet()) {
            int index = 0;
            while(molecule.indexOf(formulaEntry.getKey(), index) >= 0) {
                int matchingIndex = molecule.indexOf(formulaEntry.getKey(), index);
                for (String out : formulaEntry.getValue()) {
                    String result = molecule.substring(0, matchingIndex) + out + molecule.substring(matchingIndex + formulaEntry.getKey().length());
                    results.add(result);
                }
                index = matchingIndex + 1;
            }
        }
        return results;
    }

    private static String fillFormulas(List<String> lineInput) {
        boolean isMolecule = false;
        String molecule = "";
        for (String line : lineInput) {
            if(StringUtils.isBlank(line)) {
                isMolecule = true;
            } else {
                if(isMolecule) {
                    molecule = line;
                } else {
                    String in = ReaderUtil.stringBefore(line, " => ");
                    String out = ReaderUtil.stringAfter(line, " => ");
                    if(!formulas.containsKey(in)) {
                        formulas.put(in, new ArrayList<>());
                    }
                    formulas.get(in).add(out);
                }
            }
        }
        return molecule;
    }

    private static String fillFormulasBackwards(List<String> lineInput) {
        boolean isMolecule = false;
        String molecule = "";
        for (String line : lineInput) {
            if(StringUtils.isBlank(line)) {
                isMolecule = true;
            } else {
                if(isMolecule) {
                    molecule = line;
                } else {
                    String in = ReaderUtil.stringBefore(line, " => ");
                    String out = ReaderUtil.stringAfter(line, " => ");
                    if(!formulas.containsKey(out)) {
                        formulas.put(out, new ArrayList<>());
                    }
                    formulas.get(out).add(in);
                }
            }
        }
        return molecule;
    }


    private static int minSteps = Integer.MAX_VALUE;

    private static List<String> lengthSorted;

    public static void second(List<String> lineInput) {
        String endMolecule = fillFormulasBackwards(lineInput);

        lengthSorted = new ArrayList<>(formulas.keySet());
        lengthSorted.sort(Comparator.comparing(String::length).reversed());

        System.out.println(lengthSorted);

//        processMolecule(endMolecule, 0);

        System.out.println("Total: " + endMolecule.length());
        System.out.println("W: " + StringUtils.countMatches(endMolecule,"W"));
        System.out.println("X: " + StringUtils.countMatches(endMolecule,"X"));
        System.out.println("Y: " + StringUtils.countMatches(endMolecule,"Y"));
        System.out.println("Z: " + StringUtils.countMatches(endMolecule,"Z"));

        System.out.println(endMolecule.length() - 2 * (StringUtils.countMatches(endMolecule,"X") + StringUtils.countMatches(endMolecule,"Y") + StringUtils.countMatches(endMolecule,"Z")) - 1);

    }

    private static void processMolecule(String molecule, int step) {
        if(step >= minSteps) {
            return;
        }
        if(molecule.contains("e")) {
            if(molecule.length() == 1) {
                if (step < minSteps) {
                    minSteps = step;
                    System.out.println("New min steps: " + minSteps);
                }
            }
        } else {
            for (String transformer : lengthSorted) {
                Set<String> transformedList = transform(molecule, transformer);
                for (String transformed : transformedList) {
                    processMolecule(transformed, step + 1);
                }
            }
        }
    }

    private static Set<String> transform(String molecule, String transformer) {
        Set<String> results = new HashSet<>();
        int index = 0;
        while(molecule.indexOf(transformer, index) >= 0) {
            int matchingIndex = molecule.indexOf(transformer, index);
            for (String out : formulas.get(transformer)) {
                String result = molecule.substring(0, matchingIndex) + out + molecule.substring(matchingIndex + transformer.length());
                results.add(result);
            }
            index = matchingIndex + 1;
        }
        return results;
    }

}
