package painting;

import layout.Dimensions;
import layout.LayoutBox;
import layout.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Paint {

    public Paint() {
    }

    public BufferedImage paint(LayoutBox layoutBoxRoot, int canvasWidth, int canvasHeight) {

        ArrayList<SolidColor> displayList = buildDispalyList(layoutBoxRoot);
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        for (SolidColor solidColor : displayList) {
            canvas.paintItem(solidColor);
        }
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, canvas.pixels.get(x + y * canvasWidth).getRGB());
            }
        }
        return image;
    }

    public ArrayList<SolidColor> buildDispalyList(LayoutBox layoutBox) {
        ArrayList<SolidColor> dispalyList = new ArrayList<>();
        renderLayoutBox(dispalyList, layoutBox);
        return dispalyList;
    }

    private void renderLayoutBox(ArrayList<SolidColor> dispalyList, LayoutBox layoutBox) {
        renderBackground(dispalyList, layoutBox);
        renderBorders(dispalyList, layoutBox);

        for (LayoutBox child : layoutBox.children) {
            renderLayoutBox(dispalyList, child);
        }
    }

    private void renderBackground(ArrayList<SolidColor> dispalyList, LayoutBox layoutBox) {
        String col = getColor(layoutBox, "background");
        String op = layoutBox.boxType.styledNode.getAttValue("opacity").toString();
        int opacity = 255;
        if (!op.equals("none")) {
            opacity = Integer.parseInt(op);
        }
        Color color = new Color(Integer.parseInt(col.substring(1), 16));
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        Color c = new Color(r, g, b, opacity);
        dispalyList.add(new SolidColor(c, layoutBox.dimensions.borderBox()));
    }

    private String getColor(LayoutBox layoutBox, String name) {
        switch (layoutBox.boxType.type) {
            case BlockNode:
            case InlineNode:
                if (!layoutBox.boxType.styledNode.getAttValue(name).toString().equals("none")) {
                    return layoutBox.boxType.styledNode.getAttValue(name).toString();
                } else {
                    return "none";
                }
            case AnonymousBlock:
                return "none";
        }
        return "none";
    }

    private void renderBorders(ArrayList<SolidColor> dispalyList, LayoutBox layoutBox) {
        String col = getColor(layoutBox, "border-color");
        // 边框没颜色的话直接返回即可
        if (col.equals("none")) {
            return;
        }

        Dimensions d = layoutBox.dimensions;
        Rect borderBox = d.borderBox();
        Color color = new Color(Integer.parseInt(col.substring(1), 16));

        // 左边框
        dispalyList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                d.border.left,
                borderBox.height)));
        // 右边框
        dispalyList.add(new SolidColor(color, new Rect(
                borderBox.x + borderBox.width - d.border.right,
                borderBox.y,
                d.border.right,
                borderBox.height)));
        // 顶边框
        dispalyList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                borderBox.width,
                d.border.top)));
        // 底边框
        dispalyList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y + borderBox.height - d.border.bottom,
                borderBox.width,
                d.border.bottom)));
    }

}
