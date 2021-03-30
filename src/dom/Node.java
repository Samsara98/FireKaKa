package dom;

import java.util.ArrayList;
import java.util.Map;

public class Node {

    public ArrayList<Node> children;
    public Map<String, String> attrs;
    public String nodeType;
    public String tagName;

    public Node() {

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return sout(this, stringBuilder, 0).toString();
    }

    private StringBuilder sout(Node node, StringBuilder stringBuilder, int num) {
        String indent = "  ";
        stringBuilder.append(indent.repeat(num)).append("<").append(node.tagName);
        if (node.attrs != null) {
            for (Map.Entry<String, String> entry : node.attrs.entrySet()) {
                stringBuilder.append(" ").append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"");
            }
        }
        stringBuilder.append(">\n");
        for (Node no : node.children) {
            if (no.nodeType.equals("text")) {
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                stringBuilder.append(no.toString());
                num = 0;
                continue;
            }
            stringBuilder = sout(no, stringBuilder, num + 1);
        }
        stringBuilder.append(indent.repeat(num)).append("</").append(node.tagName).append(">\n");
        return stringBuilder;
    }
}
