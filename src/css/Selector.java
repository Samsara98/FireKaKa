package css;

import java.util.ArrayList;
import java.util.Objects;

public class Selector {

    public String tagName;
    public String id;
    public ArrayList<String> className;

    public Selector(String id, ArrayList<String> className, String tagName) {
        this.id = id;
        this.className = Objects.requireNonNullElseGet(className, ArrayList::new);
        this.tagName = tagName;
    }

    public int getSpecificity() {

        int specificity = 0;
        if (!id.equals("")) {
            specificity += 99999;
        }
        specificity += 3 * className.size();

        if (tagName.equals("*") || tagName.equals("")) {
            specificity += 1;
        } else {
            specificity += 2;
        }
        return specificity;

    }
}
