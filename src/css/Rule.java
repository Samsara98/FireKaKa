package css;

import java.util.ArrayList;

class Rule {

    ArrayList<Selector> selectors;
    ArrayList<Declaration> declarations;

    public Rule(ArrayList<Selector> selectors, ArrayList<Declaration> declarations) {
        this.selectors = selectors;
        this.declarations = declarations;
    }

}
