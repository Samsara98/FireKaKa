package css;

import java.util.ArrayList;

public class Rule {

    ArrayList<Selector> selectors;
    ArrayList<Declaration> declarations;

    public Rule(ArrayList<Selector> selectors, ArrayList<Declaration> declarations) {
        this.selectors = selectors;
        this.declarations = declarations;
    }

    public ArrayList<Selector> getSelectors() {
        return selectors;
    }

    public void setSelectors(ArrayList<Selector> selectors) {
        this.selectors = selectors;
    }

    public ArrayList<Declaration> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(ArrayList<Declaration> declarations) {
        this.declarations = declarations;
    }


}
