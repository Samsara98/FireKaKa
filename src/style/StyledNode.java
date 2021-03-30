package style;

import css.*;
import dom.ElementNode;
import dom.Node;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return sout(this, stringBuilder, 0).toString();
    }

    private StringBuilder sout(StyledNode styledNode, StringBuilder stringBuilder, int num) {
        String indent = "  ";
        stringBuilder.append(indent.repeat(num)).append("<").append(styledNode.domNode.tagName);
        if (styledNode.propertyMap != null) {
            for (Map.Entry<String, Value> entry : styledNode.propertyMap.entrySet()) {
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

    private MatchedRule matchRule(ElementNode elementNode, Rule rule) {
        for (Selector selector : rule.getSelectors()) {
            if (mathches(elementNode, selector)) {
                return new MatchedRule(selector.getSpecificity(), rule);
            }
        }
        return null;
    }

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

    private Map<String, Value> specifiedValues(ElementNode elementNode, Stylesheet stylesheet) {

        Map<String, Value> values = new LinkedHashMap<>();
        ArrayList<MatchedRule> rules = matchRules(elementNode, stylesheet);
        rules = rules.stream().sorted(Comparator.comparingInt(a -> a.specificity)).collect(Collectors.toCollection(ArrayList::new));
        for (MatchedRule matchRule : rules) {
            for (Declaration declaration : matchRule.rule.getDeclarations()) {
                values.put(declaration.key, declaration.value);
            }
        }
        return values;
    }

    private boolean mathches(ElementNode elementNode, Selector selector) {

        if (elementNode.tagName != null && !elementNode.tagName.equals(selector.tagName) && !selector.tagName.equals("*") && !selector.tagName.equals("")) {
            return false;
        }
        if (!elementNode.getID().equals(selector.id) && !selector.id.equals("")) {
            return false;
        }
        if (selector.className.size() == 0) {
            return true;
        }
        for (String s : selector.className) {
            if (elementNode.getClassName().contains(s)) {
                return true;
            }
        }
        return false;
    }

}
