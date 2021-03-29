package css;

import java.util.ArrayList;
import java.util.Objects;

class Selector {

    String tagName;
    String id;
    ArrayList<String> className;

    public Selector(String id, ArrayList<String> className, String tagName) {
        this.id = id;
        this.className = Objects.requireNonNullElseGet(className, ArrayList::new);
        this.tagName = tagName;
    }

    public int getSpecificity() {
        if (!id.equals("")) {
            return 4;
        }
        if (className.size() != 0) {
            return 3;
        }
        if (tagName.equals("*") || tagName.equals("")) {
            return 1;
        }
        return 2;

    }
}
