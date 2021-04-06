package layout;

public class Rect {
    public int x;
    public int y;
    public int width;
    public int height;

    public Rect() {
    }

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect expandedBy(EdgeSize edgeSize) {
        return new Rect(x - edgeSize.left, y - edgeSize.top, width + edgeSize.left + edgeSize.right, height + edgeSize.top + edgeSize.bottom);
    }

    @Override
    public String toString() {
        return "Rect{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
