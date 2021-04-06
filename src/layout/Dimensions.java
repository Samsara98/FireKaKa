package layout;

public class Dimensions {

    public Rect content;
    public EdgeSize padding;
    public EdgeSize border;
    public EdgeSize margin;

    public Dimensions() {
        content = new Rect();
        padding = new EdgeSize();
        border = new EdgeSize();
        margin = new EdgeSize();
    }
    // 内容区域加内边距（padding）
    public Rect paddingBox() {
        return content.expandedBy(padding);
    }
    // 内容区域加内边距（padding）和边框（border）
    public Rect borderBox() {
        return paddingBox().expandedBy(border);
    }
    // 内容区域加内边距（padding）、边框（border）、外边距（margin）
    public Rect marginBox() {
        return borderBox().expandedBy(margin);
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "content=" + content +
                ", padding=" + padding +
                ", border=" + border +
                ", margin=" + margin +
                '}';
    }
}
