package css;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class Rule {

    ArrayList<Selector> selectors;
    LinkedHashMap<String, String> declarations;

    public Rule(ArrayList<Selector> selectors, LinkedHashMap<String, String> declarations) {
        this.selectors = selectors;
        this.declarations = declarations;
    }

}
