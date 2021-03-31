package dom;

public class TextNode extends Node{

    String text;
    public TextNode(String text){
        super();
        nodeType = "text";
        this.tagName = "";
        this.text = text;
    }
    public TextNode(){
        super();
    }

    @Override
    public String toString() {
        return text;
    }
}
