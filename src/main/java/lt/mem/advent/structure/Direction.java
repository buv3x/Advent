package lt.mem.advent.structure;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private final int value;

    private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Direction of(int value) {
        for (Direction direction : values()) {
            if(direction.value == value) {
                return direction;
            }
        }
        return null;
    }
}
