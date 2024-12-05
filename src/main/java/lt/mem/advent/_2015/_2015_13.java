package lt.mem.advent._2015;

import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2015_13 {

    private static final Map<String, Map<String, Integer>> distances = new HashMap<>();

    private static Integer totalDistance = Integer.MIN_VALUE;

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2015/13.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        fillDistances(lineInput);
        List<String> guests = new ArrayList<>();
        guests.add(distances.keySet().iterator().next());
        addGuest(guests);

        System.out.println(totalDistance);
    }

    private static void addGuest(List<String> guests) {
        if(guests.size() == distances.size()) {
            int distance = 0;
            for(int i = 0; i < guests.size() - 1; ++i) {
                distance += distances.get(guests.get(i)).get(guests.get(i + 1));
                distance += distances.get(guests.get(i + 1)).get(guests.get(i));
            }
            distance += distances.get(guests.get(0)).get(guests.get(guests.size() - 1));
            distance += distances.get(guests.get(guests.size() - 1)).get(guests.get(0));
            if(distance > totalDistance) {
                totalDistance = distance;
            }
        } else {
            String lastGuest = guests.get(guests.size() - 1);
            for (String key : distances.get(lastGuest).keySet()) {
                if(!guests.contains(key)) {
                    List<String> newGuests = new ArrayList<>(guests);
                    newGuests.add(key);
                    addGuest(newGuests);
                }
            }
        }
    }

    private static void fillDistances(List<String> lineInput) {
        for (String line : lineInput) {
            String name = line.substring(0, line.indexOf(' '));
            if(!distances.containsKey(name)) {
                distances.put(name, new HashMap<>());
            }
            String secondName = ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "sitting next to "), ".");
            if(line.contains("gain")) {
                int gain = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "gain "), " happiness"));
                distances.get(name).put(secondName, gain);
            } else {
                int loss = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "lose "), " happiness"));
                distances.get(name).put(secondName, -loss);
            }
        }
    }

    public static void second(List<String> lineInput) {
        fillDistances(lineInput);
        Set<String> allGuests = distances.keySet();
        distances.put("X", new HashMap<>());
        for (String allGuest : allGuests) {
            distances.get("X").put(allGuest, 0);
            distances.get(allGuest).put("X", 0);
        }



        List<String> guests = new ArrayList<>();
        guests.add(distances.keySet().iterator().next());
        addGuest(guests);

        System.out.println(totalDistance);
    }

}
