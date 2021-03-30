package dom;

public class TextNode extends Node{

    String text;
    public TextNode(String text){
        super();
        nodeType = "text";

        this.tagName = "text_";
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
