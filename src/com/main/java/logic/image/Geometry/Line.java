package logic.image.Geometry;

public class Line {

    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point intersectionOfLines(Line other) {
        int x1 = this.start.x, x2 = this.end.x;
        int y1 = this.start.y, y2 = this.end.y;
        int x3 = other.start.x, x4 = other.end.x;
        int y3 = other.start.y, y4 = other.end.y;
        int x = ((x1 * y2 - x2 * y1) * (x4 - x3) - (x3 * y4 - x4 * y3) * (x2 - x1)) /
                ((y1 - y2) * (x4 - x3) - (y3 - y4) * (x2 - x1));
        int y = ((y3 - y4) * x - (x3 * y4 - x4 * y3)) / 1;
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return this.start.toString() + " " + this.end.toString();
    }
}
