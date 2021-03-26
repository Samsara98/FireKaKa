import dom.ElementNode;
import dom.Node;
import dom.TextNode;

public class DOMTest {
    public static void main(String[] args) {
        ElementNode elementNode = new ElementNode();
        TextNode textNode = new TextNode();

        //noinspection ConstantConditions
        assert elementNode instanceof Node;
        //noinspection ConstantConditions
        assert textNode instanceof Node;
    }
}