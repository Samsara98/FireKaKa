package dom;

import java.util.ArrayList;
import java.util.Map;

public class ElementNode extends Node{

    public ElementNode(String tagName, Map<String,String> attrs, ArrayList<Node> children){
        super();
        nodeType = "element";
        this.tagName = tagName;
        this.attrs = attrs;
        this.children = children;
    }
    public ElementNode(){
        super();
    }
}
