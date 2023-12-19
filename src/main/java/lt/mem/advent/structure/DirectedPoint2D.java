package lt.mem.advent.structure;

import java.util.Objects;

public class DirectedPoint2D {

    private Point2D point;

    private Direction direction;

    public DirectedPoint2D(Point2D point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    public Point2D getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedPoint2D that = (DirectedPoint2D) o;
        return Objects.equals(point, that.point) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, direction);
    }
}
