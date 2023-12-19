package lt.mem.advent.structure;

public enum Turn {

    FORWARD(0),
    LEFT(3),
    RIGHT(1),
    BACK(2);

    private final int value;

    private Turn(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
