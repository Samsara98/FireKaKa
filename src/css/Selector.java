package css;

import java.util.ArrayList;

class Selector {

    String tagName;
    String id;
    ArrayList<String> className;

    public Selector(String id, ArrayList<String> className, String tagName) {
        this.id = id;
        if (className == null) {
            this.className = new ArrayList<>();
        } else {
            this.className = className;
        }
        this.tagName = tagName;
    }

    public int getSpecificity() {
        if (!id.equals("")) {
            return 4;
        }
        if (className != null) {
            return 3;
        }
        if (tagName.equals("*") || tagName.equals("")) {
            return 1;
        }
        return 2;

    }
}
