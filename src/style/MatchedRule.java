package style;

import css.Rule;

public class MatchedRule {

    public int specificity;
    public Rule rule;

    public MatchedRule(int specificity, Rule rule) {
        this.specificity = specificity;
        this.rule = rule;
    }

}
