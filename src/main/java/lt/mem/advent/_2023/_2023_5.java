package lt.mem.advent._2023;

import lt.mem.advent.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class _2023_5 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/5.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        List<List<Long>> seedRoutes = new ArrayList<>();

        List<Long> seeds = Arrays.stream(StringUtils.split(input.get(0).substring(input.get(0).indexOf(":") + 2), ' ')).map(Long::valueOf).collect(Collectors.toList());
        for (Long seed : seeds) {
            List<Long> seedRoute = new ArrayList<>();
            seedRoute.add(seed);
            seedRoutes.add(seedRoute);
        }

        List<String> mapStrings = new ArrayList<>();
        for (int i = 3; i < input.size(); ++i) {
            String line = input.get(i);
            if (StringUtils.isBlank(line)) {
                process(seedRoutes, mapStrings);

                mapStrings.clear();
            } else if(Character.isDigit(line.charAt(0))) {
                mapStrings.add(line);
            }
        }
        process(seedRoutes, mapStrings);
        System.out.println(seedRoutes);
        System.out.println(seedRoutes.stream().map(s -> s.get(s.size() - 1)).min(Comparator.comparing(Function.identity())).get());
    }


    public static void second(List<String> input) {
        List<SeedRange> seedRanges = new LinkedList<>();
        List<Long> seeds = Arrays.stream(StringUtils.split(input.get(0).substring(input.get(0).indexOf(":") + 2), ' ')).map(Long::valueOf).collect(Collectors.toList());
        for (int i = 0; i < seeds.size() / 2; ++i) {
            Long seed = seeds.get(i * 2);
            Long range = seeds.get((i * 2) + 1);
            seedRanges.add(new SeedRange(seed, seed + range - 1));
        }
        seedRanges.sort(Comparator.comparing(Function.identity()));
        System.out.println(seedRanges);

        List<String> mapStrings = new ArrayList<>();
        for (int i = 3; i < input.size(); ++i) {
            String line = input.get(i);
            if (StringUtils.isBlank(line)) {
                seedRanges = process2(seedRanges, mapStrings);
                mapStrings.clear();
            } else if(Character.isDigit(line.charAt(0))) {
                mapStrings.add(line);
            }
        }
        process2(seedRanges, mapStrings);
    }


    private static void process(List<List<Long>> seedRoutes, List<String> mapStrings) {
        Set<MapRange> mapRanges = new TreeSet<>();
        for (String mapString : mapStrings) {
            String[] split = StringUtils.split(mapString, ' ');
            mapRanges.add(new MapRange(Long.valueOf(split[1]), Long.valueOf(split[0]), Long.valueOf(split[2]) + Long.valueOf(split[1]) - 1));
        }
//                System.out.println(mapRanges);
        // process;
        for (List<Long> seedRoute : seedRoutes) {
            Long seed = seedRoute.get(seedRoute.size() - 1);
            Long newSeed = seed;
            for (MapRange mapRange : mapRanges) {
                if(seed >= mapRange.sourceStart && seed <= mapRange.sourceEnd) {
                    newSeed = mapRange.destStart + (seed - mapRange.sourceStart);
                }
            }
            seedRoute.add(newSeed);
        }
    }

    private static List<SeedRange> process2(Collection<SeedRange> seedRanges, List<String> mapStrings) {
        Set<MapRange> mapRanges = new TreeSet<>();
        for (String mapString : mapStrings) {
            String[] split = StringUtils.split(mapString, ' ');
            mapRanges.add(new MapRange(Long.valueOf(split[1]), Long.valueOf(split[0]), Long.valueOf(split[2]) + Long.valueOf(split[1]) - 1));
        }
//                System.out.println(mapRanges);
        LinkedList<SeedRange> toProcess = new LinkedList<>(seedRanges);
        LinkedList<SeedRange> processed = new LinkedList<>();

        while(toProcess.size() > 0) {
            SeedRange seedRange = toProcess.poll();
            boolean found = false;
            for (MapRange mapRange : mapRanges) {
                if(mapRange.sourceStart <= seedRange.end && seedRange.start <= mapRange.sourceEnd) {
                    Long start = Math.max(mapRange.sourceStart, seedRange.start);
                    Long end = Math.min(mapRange.sourceEnd, seedRange.end);
                    processed.add(new SeedRange(mapRange.destStart + start - mapRange.sourceStart,
                            mapRange.destStart + end - mapRange.sourceStart));

                    if(seedRange.start < mapRange.sourceStart) {
                        toProcess.add(new SeedRange(seedRange.start, mapRange.sourceStart - 1));
                    }

                    if(seedRange.end > mapRange.sourceEnd) {
                        toProcess.add(new SeedRange(mapRange.sourceEnd + 1, seedRange.end));
                    }
                    found = true;
                    break;
                }
            }

            if(!found) {
                processed.add(seedRange);
            }
        }
        processed.sort(Comparator.comparing(Function.identity()));
        System.out.println(processed);
        return processed;
    }

    private static class SeedRange implements Comparable<SeedRange> {

        private final Long start;

        private final Long end;

        public SeedRange(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(SeedRange o) {
            return start.compareTo(o.start);
        }

        @Override
        public String toString() {
            return "(" + start +
                    "-" + end +
                    ')';
        }
    }

    private static class MapRange implements Comparable<MapRange> {

        private final Long sourceStart;

        private final Long destStart;

        private final Long sourceEnd;

        public MapRange(Long sourceStart, Long destStart, Long sourceEnd) {
            this.sourceStart = sourceStart;
            this.destStart = destStart;
            this.sourceEnd = sourceEnd;
        }

        @Override
        public int compareTo(MapRange o) {
            return sourceStart.compareTo(o.sourceStart);
        }

        @Override
        public String toString() {
            return "((" + sourceStart + "-" + sourceEnd + ") -> " + destStart + ")";
        }
    }

}
