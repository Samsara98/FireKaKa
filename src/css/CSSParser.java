package css;

import html.Parser;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class CSSParser extends Parser {

    public CSSParser() {

    }

    public Stylesheet parse(String input) {
        this.input = input;
        return new Stylesheet(parseRules());
    }

    private ArrayList<Rule> parseRules() {

        ArrayList<Rule> rules = new ArrayList<>();
        while (!finish()) {
            rules.add(parseRule());
        }
        return rules;
    }

    private Rule parseRule() {
        return new Rule(parseSelectors(), parseDeclarations());
    }

    private LinkedHashMap<String, String> parseDeclarations() {

        LinkedHashMap<String, String> declarations = new LinkedHashMap<>();
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
            declarations.put(key, value);
        }
        assert consumeChar() == '}';
        return declarations;
    }

    private Selector parseSelector() {
        Selector selector = new Selector("", null, "");
        label:
        while (!finish()) {
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
                case ',':
                case ' ':
                    consumeChar();
                    break label;
            }
        }
        return selector;
    }

    private ArrayList<Selector> parseSelectors() {
        ArrayList<Selector> selectors = new ArrayList<>();
        while (true) {
            consumeWhiteSpace();
            if (finish() || startWith(1, new char[]{'{'})) {
                break;
            }
            selectors.add(parseSelector());
        }
        return selectors;
    }

    private String parseIdentifier() {
        StringBuilder stringBuffer = new StringBuilder();
        while (Pattern.matches("[A-Za-z0-9]", String.valueOf(currentChar()))) {
            stringBuffer.append(consumeChar());
        }
        return stringBuffer.toString();
    }

}
