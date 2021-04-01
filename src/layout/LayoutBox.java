package layout;

import style.StyledNode;

import java.util.ArrayList;
import java.util.List;

public class LayoutBox {

    Dimensions dimensions;
    BoxType boxType;
    List<LayoutBox> children;

    public LayoutBox() {
    }

    public LayoutBox(StyledNode styledNode) {

        children = new ArrayList<>();
        assert !styledNode.display().equals("none");
        switch (styledNode.display()) {
            case "block":
                this.boxType = BoxType.BlockNode;
                break;
            case "inline":
                this.boxType = BoxType.InlineNode;
                break;
        }
        for (StyledNode child : styledNode.children) {
            switch (child.display()) {
                case "block":
                    children.add(new LayoutBox(child));
                    break;
                case "inline":
                    getInlineContainer().children.add(new LayoutBox(child));
                    break;
            }
        }
    }

    private LayoutBox getInlineContainer() {

        switch (boxType) {
            case InlineNode:
            case AnonymousBlock:
                return this;
            case BlockNode:
                //若没有创建过匿名Box
                if (children.get(children.size() - 1).boxType != BoxType.AnonymousBlock) {
                    LayoutBox anonymousBlock = new LayoutBox();
                    anonymousBlock.boxType = BoxType.AnonymousBlock;
                    children.add(anonymousBlock);
                }
                return children.get(children.size() - 1);
        }
        return null;
    }

    public void layoutTree(int i) {
    }


    public enum BoxType {
        BlockNode, InlineNode, AnonymousBlock
    }

    private class Dimensions {

        Rect content;
        EdgeSize padding;
        EdgeSize border;
        EdgeSize margin;
    }

    private class Rect {
        int x;
        int y;
        int width;
        int height;
    }

    private class EdgeSize {
        int left;
        int right;
        int top;
        int bottom;
    }
}
