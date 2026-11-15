package lt.mem.advent._2017;

import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class _2017_20 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/20.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }


    public static void first(List<String> lineInput) {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            List<Long> position = Arrays.stream(
                    StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "p=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            List<Long> velocity = Arrays.stream(
                    StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "v=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            List<Long> acceleration = Arrays.stream(
                    StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "a=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            long totalA = 0L;
            for (int j = 0; j < acceleration.size(); ++j) {
                totalA += Math.abs(acceleration.get(j) * 1000 + velocity.get(j));
            }
            Particle particle = new Particle(i, position, velocity, acceleration);
            particle.absoluteAccelaration = totalA;
            particles.add(particle);
        }
//        System.out.println(particles);
        particles.sort(Comparator.comparing(p -> p.absoluteAccelaration));
        System.out.println(particles.get(0).index);

    }

    public static void second(List<String> lineInput) {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            List<Long> position = Arrays.stream(
                            StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "p=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            List<Long> velocity = Arrays.stream(
                            StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "v=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            List<Long> acceleration = Arrays.stream(
                            StringUtils.split(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "a=<"), ">"), ','))
                    .sequential().map(StringUtils::trim).map(Long::valueOf).collect(Collectors.toList());
            Particle particle = new Particle(i, position, velocity, acceleration);
            particle.position = new ArrayList<>(particle.initialPosition);
            particle.velocity = new ArrayList<>(particle.initialVelocity);
            particles.add(particle);
        }

        Set<Integer> duplicates = new HashSet<>();
        getDuplicates(duplicates, particles);
        System.out.println("0: " + duplicates);

        for(int i = 1; i <= 50; ++i) {
            for (Particle particle : particles) {
                if(duplicates.contains(particle.index)) {
                    continue;
                }
                List<Long> newVelocity = new ArrayList<>();
                List<Long> newPosition = new ArrayList<>();
                for(int k = 0; k < 3; k++) {
                    newVelocity.add(particle.velocity.get(k) + particle.initialAcceleration.get(k));
                    newPosition.add(particle.position.get(k) + newVelocity.get(k));
                }
                particle.velocity = newVelocity;
                particle.position = newPosition;
            }
            getDuplicates(duplicates, particles);
            System.out.println(i + ": " + duplicates);
        }

        System.out.println(particles.size() - duplicates.size());

    }

    private static void getDuplicates(Set<Integer> duplicates, List<Particle> particles) {
        Map<String, List<Particle>> map = new HashMap<>();
        for (Particle particle : particles) {
            if(duplicates.contains(particle.index)) {
                continue;
            }
            String key = particle.position.get(0) + "_" + particle.position.get(1) + "_" + particle.position.get(2);
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
                map.get(key).add(particle);
            } else {
                map.get(key).add(particle);
                duplicates.addAll(map.get(key).stream().map(p -> p.index).collect(Collectors.toSet()));
            }
        }
    }

    private static boolean isPerfectSquare(long value) {
        long x = (long) Math.sqrt(value);
        return x * x == value;
    }

    private static class Particle {
        List<Long> initialPosition;
        List<Long> initialVelocity;
        List<Long> initialAcceleration;
        List<Long> position;
        List<Long> velocity;
        int index;
        long absoluteAccelaration;

        public Particle(int index, List<Long> initialPosition, List<Long> initialVelocity, List<Long> initialAcceleration) {
            this.initialPosition = initialPosition;
            this.initialVelocity = initialVelocity;
            this.initialAcceleration = initialAcceleration;
            this.index = index;
        }

        @Override
        public String toString() {
            return "{" +
                    "index=" + index +
                    ", initialPosition=" + initialPosition +
                    ", initialVelocity=" + initialVelocity +
                    ", initialAcceleration=" + initialAcceleration +
                    '}';
        }
    }

    //        Map<Pair<Integer, Integer>, List<List<Long>>> collisionsMap = new LinkedHashMap<>();
//        for(int i = 0; i < particles.size(); ++i) {
//            for(int j = i + 1; j < particles.size(); ++j) {
//                Particle particle1 = particles.get(i);
//                Particle particle2 = particles.get(j);
//                List<List<Long>> collisionsList = new ArrayList<>();
//                for(int k = 0; k < 3; k++) {
//                    List<Long> collisions = new ArrayList<>();
//                    long acceleration = particle1.initialAcceleration.get(k) - particle2.initialAcceleration.get(k);
//                    long velocity = particle1.initialVelocity.get(k) - particle2.initialVelocity.get(k);
//                    long position = particle1.initialPosition.get(k) - particle2.initialPosition.get(k);
//                    if(acceleration == 0L) {
//                        if(velocity != 0L) {
//                            if (position % velocity == 0 && -position / velocity >= 0) {
//                                collisions.add(-position / velocity);
//                            }
//                        } else {
//                            if(position == 0L) {
//                                collisions.add(-1L);
//                            }
//                        }
//                    } else {
//                        long discriminant = ((acceleration + (2 * velocity)) * (acceleration + (2 * velocity))) - (8 * acceleration * position);
//                        if(discriminant == 0L) {
//                            if(velocity % (2 * acceleration) == 0 && -velocity / (2 * acceleration) >= 0) {
//                                collisions.add(-velocity % (2 * acceleration));
//                            }
//                        } else if (discriminant > 0) {
//                            if(isPerfectSquare(discriminant)) {
//                                long top1 = -velocity + (long) Math.sqrt(discriminant);
//                                if(top1 % (2 * acceleration) == 0 && top1 / (2 * acceleration) >= 0) {
//                                    collisions.add(top1 / (2 * acceleration));
//                                }
//                                long top2 = -velocity - (long) Math.sqrt(discriminant);
//                                if(top2 % (2 * acceleration) == 0 && top2 / (2 * acceleration) >= 0) {
//                                    collisions.add(top2 / (2 * acceleration));
//                                }
//                            }
//                        }
//                    }
//                    collisionsList.add(collisions);
//                }
////                System.out.println(i + " " + j + ": " + collisionsList);
//                if(collisionsList.get(0).size() > 0 && collisionsList.get(1).size() > 0 && collisionsList.get(2).size() > 0) {
//                    collisionsMap.put(Pair.of(i, j), collisionsList);
//                }
//            }
//        }
//
//        int counter = 0;
//        for (Map.Entry<Pair<Integer, Integer>, List<List<Long>>> entry : collisionsMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//            counter++;
//            if(counter > 200) {
//                break;
//            }
//        }



}
