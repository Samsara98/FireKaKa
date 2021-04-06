package css;

import html.Parser;

import javax.swing.text.html.CSS;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CSSParser extends Parser {

    String css;

    public CSSParser(){}
    public CSSParser(String css) {
        this.css = css;
    }

    public Stylesheet parse(){
        return parse(css);
    }

    public Stylesheet parse(String input) {
        if(input==null){
            return null;
        }
        this.input = input;
        return new Stylesheet(parseRules());
    }

    /**
     * 解析多个rule
     *
     * @return
     */
    private ArrayList<Rule> parseRules() {

        ArrayList<Rule> rules = new ArrayList<>();
        while (!finish()) {
            rules.add(parseRule());
            consumeWhiteSpace();
        }
        return rules;
    }

    /**
     * 解析单个rule
     *
     * @return
     */
    private Rule parseRule() {
        return new Rule(parseSelectors(), parseDeclarations());
    }

    private ArrayList<Declaration> parseDeclarations() {

        ArrayList<Declaration> declarations = new ArrayList<>();
        assert currentChar() == '{';
        consumeChar();
        while (true) {
            consumeWhiteSpace();
            if (currentChar() == '}') {
                break;
            }
            String key = consumeWhile(c -> currentChar() != c, ':');
            assert consumeChar() == ':';
            consumeWhiteSpace();
            String value = consumeWhile(c -> currentChar() != c, ';');
            assert consumeChar() == ';';
            declarations.add(new Declaration(key, value));
        }
        assert consumeChar() == '}';
        return declarations;
    }

    /**
     * 解析单个选择器
     *
     * @return
     */
    private Selector parseSelector() {
        Selector selector = new Selector("", null, "");
        label:
        while (!finish()) {
            consumeWhiteSpace();
            if (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
                selector.tagName = parseIdentifier();
            }
            switch (currentChar()) {
                case '#':
                    consumeChar();
                    selector.id = parseIdentifier();
                    break;
                case '.':
                    consumeChar();
                    selector.className.add(parseIdentifier());
                    break;
                case '*':
                    consumeChar();
                    selector.tagName = "*";
                    break;
                case '{':
                    break label;
                case ',':
                    consumeChar();
                    break label;
            }
        }
        return selector;
    }

    /**
     * 解析多个选择器
     *
     * @return
     */
    private ArrayList<Selector> parseSelectors() {
        ArrayList<Selector> selectors = new ArrayList<>();
        while (true) {
            consumeWhiteSpace();
            if (finish() || startWith(1, new char[]{'{'})) {
                break;
            }
            selectors.add(parseSelector());
        }
        if (selectors.size() == 0) {
            selectors.add(new Selector("", null, ""));
        }
        return selectors.stream().sorted(Comparator.comparingInt(Selector::getSpecificity)).collect(Collectors.toCollection(ArrayList<Selector>::new));
    }

    /**
     * 解析选择器标签
     *
     * @return
     */
    private String parseIdentifier() {
        StringBuilder stringBuilder = new StringBuilder();
        consumeWhiteSpace();
        while (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
            stringBuilder.append(consumeChar());
        }
        return stringBuilder.toString();
    }

}
