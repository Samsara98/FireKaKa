package html;

import dom.ElementNode;
import dom.Node;
import dom.TextNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HTMLParser extends Parser {

    String html;

    public HTMLParser(String html) {
        this.html = html;
    }

    public Node parse() {

        return parse(html);
    }

    /**
     * 解析html为node对象，若无html标签自动添加
     *
     * @param input html-String
     * @return ElementNode对象
     */
    public Node parse(String input) {
        this.input = input;
        ArrayList<Node> nodes = parseNodes();
        //只有一个node则为html标签node
        if (nodes.size() == 1) {
            return nodes.get(0);
        } else {
            return new ElementNode("html", new HashMap<String, String>(), nodes);
        }
    }

    /**
     * 解析单个节点
     *
     * @return
     */
    private Node parseNode() {

        if (currentChar() == '<') {
            return parseElement();
        } else {
            return parseTest();
        }
    }

    private ArrayList<Node> parseNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        while (true) {
            consumeWhiteSpace();
            if (finish() || startWith(2, new char[]{'<', '/'})) {
                break;
            }
            nodes.add(parseNode());
        }
        return nodes;
    }

    /**
     * 解析Element节点
     *
     * @return
     */
    private ElementNode parseElement() {

        //开始标签、属性
        assert consumeChar() == '<';
        String tagName = parseTagName();
        Map<String, String> attrs = parseAttributes();
        assert consumeChar() == '>';
        //内容
        ArrayList<Node> children = parseNodes();
        //关闭标签
        assert consumeChar() == '<';
        assert consumeChar() == '/';
        String tagName2 = parseTagName();
        assert tagName2.equals(tagName);
        assert consumeChar() == '>';
        return new ElementNode(tagName, attrs, children);
    }

    /**
     * 解析Text节点
     *
     * @return
     */
    private TextNode parseTest() {
        String text = consumeWhile(c -> currentChar() != c, '<');
        return new TextNode(text);
    }


    /**
     * 解析标签名字，只能包涵数字和字母
     *
     * @return
     */
    private String parseTagName() {

        StringBuilder stringBuffer = new StringBuilder();
        while (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
            stringBuffer.append(consumeChar());
        }
        return stringBuffer.toString();
    }

    /**
     * 解析属性，返回字符串对，要求属性名后面紧跟‘=’；
     *
     * @return
     */
    private String[] parseAttr() {
        String[] attr = new String[2];
        attr[0] = parseTagName();
        assert consumeChar() == '=';
        attr[1] = parseAttrValue();
        return attr;
    }

    /**
     * 解析属性值，要求属性使用‘或“包围，并且前后一致
     *
     * @return
     */
    private String parseAttrValue() {
        char quote = consumeChar();
        assert (quote == '"' || quote == '\'');
        String value = consumeWhile(c -> currentChar() != c, quote);
        assert consumeChar() == quote;
        return value;
    }

    /**
     * 解析多个属性
     *
     * @return
     */
    private Map<String, String> parseAttributes() {
        Map<String, String> attrMap = new HashMap<>();
        while (true) {
            consumeWhiteSpace();
            if (currentChar() == '>') {
                break;
            }
            String[] attr = parseAttr();
            attrMap.put(attr[0], attr[1]);
        }
        return attrMap;
    }
}
