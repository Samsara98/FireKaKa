package style;

import css.*;
import dom.ElementNode;
import dom.Node;
import layout.Type;

import java.util.*;
import java.util.stream.Collectors;

public class StyledNode {

    public Node domNode;
    public Map<String, Value> propertyMap;
    public ArrayList<StyledNode> children;


    public StyledNode(Node domNode, Stylesheet stylesheet) {

        Map<String, Value> propertyMap;
        if (domNode.nodeType.equals("element")) {
            propertyMap = specifiedValues((ElementNode) domNode, stylesheet);
        } else {
            propertyMap = new LinkedHashMap<>();
        }
        ArrayList<StyledNode> children = new ArrayList<>();
        if (domNode.children != null) {
            for (Node child : domNode.children) {
                children.add(new StyledNode(child, stylesheet));
            }
        }
        this.domNode = domNode;
        this.propertyMap = propertyMap;
        this.children = children;
    }

    /**
     *
     * @return display类别，
     */
    public Type display() {
        String display = getAttValue("display").toString();
        switch (display) {
            case "":
            case "inline":
            case "none":
                return Type.InlineNode;
            case "block":
                return Type.BlockNode;
            default:
                return null;
        }
    }

    public Value getAttValue(String att) {

        return Objects.requireNonNullElse(propertyMap.get(att), new Value("none"));
    }

    /**
     * 元素节点与一条规则是否匹配
     *
     * @param elementNode
     * @param rule
     * @return 匹配成功返回MatchedRule（选择器优先度，规则指针)，否则返回null
     */
    private MatchedRule matchRule(ElementNode elementNode, Rule rule) {
        //将rule的每一个选择器都与selector配对
        for (Selector selector : rule.getSelectors()) {
            if (mathches(elementNode, selector)) {
                return new MatchedRule(selector.getSpecificity(), rule);
            }
        }
        return null;
    }

    /**
     * 元素节点和CSS对象匹配
     *
     * @param elementNode
     * @param stylesheet
     * @return 返回所有匹配成功的MatchedRule
     */
    private ArrayList<MatchedRule> matchRules(ElementNode elementNode, Stylesheet stylesheet) {
        ArrayList<MatchedRule> specificities = new ArrayList<>();
        for (Rule rule : stylesheet.getRules()) {
            MatchedRule specificity = matchRule(elementNode, rule);
            if (specificity != null) {
                specificities.add(specificity);
            }
        }
        return specificities;
    }

    /**
     * 元素节点和css对象配对
     *
     * @param elementNode
     * @param stylesheet
     * @return 返回该元素节点匹配到的属性
     */
    private Map<String, Value> specifiedValues(ElementNode elementNode, Stylesheet stylesheet) {

        Map<String, Value> values = new LinkedHashMap<>();
        ArrayList<MatchedRule> rules = matchRules(elementNode, stylesheet);
        //按优先度从高到低匹配
        rules = rules.stream().sorted(Comparator.comparingInt(a -> a.specificity)).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(rules);
        for (MatchedRule matchRule : rules) {
            for (Declaration declaration : matchRule.rule.getDeclarations()) {
                if (!values.containsKey(declaration.key)) {
                    values.put(declaration.key, declaration.value);
                }
            }
        }
        return values;
    }

    /**
     * 元素节点与选择器是否匹配
     *
     * @param elementNode
     * @param selector
     * @return
     */
    private boolean mathches(ElementNode elementNode, Selector selector) {


        if (!elementNode.tagName.equals(selector.tagName) && !selector.tagName.equals("*") && !selector.tagName.equals("")) {
            return false;
        }
        if (!elementNode.getID().equals(selector.id) && !selector.id.equals("")) {
            return false;
        }
        if (selector.className.size() == 0) {
            return true;
        }
        for (String s : selector.className) {
            if (!elementNode.getClassName().contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return sout(this, stringBuilder, 0).toString();
    }

    private StringBuilder sout(StyledNode styledNode, StringBuilder stringBuilder, int num) {
        String indent = "  ";
        stringBuilder.append(indent.repeat(num)).append("<").append(styledNode.domNode.tagName);
        Map<String, Value> map = styledNode.propertyMap;
        if (map != null) {
            for (Map.Entry<String, Value> entry : map.entrySet()) {
                stringBuilder.append(" ").append(entry.getKey()).append("=").append("\"").append(entry.getValue().toString()).append("\"");
            }
        }
        stringBuilder.append(">\n");
        for (StyledNode sn : styledNode.children) {
            if (sn.domNode.nodeType.equals("text")) {
                stringBuilder.append(indent.repeat(num + 1)).append("<text></text>\n").append(indent.repeat(num));
                num = 0;
                continue;
            }
            stringBuilder = sout(sn, stringBuilder, num + 1);
        }
        stringBuilder.append(indent.repeat(num)).append("</").append(styledNode.domNode.tagName).append(">\n");
        return stringBuilder;
    }
}
