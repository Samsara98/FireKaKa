package painting;

import layout.LayoutBox;
import layout.Rect;

import java.awt.*;
import java.util.ArrayList;

public class Canvas {
    ArrayList<Color> pixels;
    int width;
    int height;

    public Canvas(int width, int height) {

        this.width = width;
        this.height = height;
        pixels = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            pixels.add(Color.WHITE);
        }
    }


    public void paintItem(DispalyCommand dispalyCommand) {
        Rect rect = dispalyCommand.rect;
        if (dispalyCommand.commandName.equals("SolidColor")) {
            int x0 = clamp(rect.x, 0, width);
            int y0 = clamp(rect.y, 0, height);
            int x1 = clamp(rect.x + rect.width, 0, width);
            int y1 = clamp(rect.y + rect.height, 0, height);

            for (int y = y0; y <= y1; y++) {
                for (int x = x0; x <= x1; x++){
                    Color color = dispalyCommand.color;
                    pixels.set(x+y*width,);
                }
            }
        }

    }

    public int clamp(int value, int min, int max) {

        if (value > max) {
            return max;
        } else {
            return Math.max(value, min);
        }

    }

    @Override
    public String toString() {
        return "Canvas{" +
                "pixels=" + pixels +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
