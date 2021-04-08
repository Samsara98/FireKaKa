package painting;

import layout.Rect;

import java.awt.*;

public class SolidColor implements DispalyCommand{

    String commandName;
    Color color;
    Rect rect;

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Rect getRect() {
        return rect;
    }

    public SolidColor(Color color, Rect rect) {
        commandName = "SolidColor";
        this.color =color;
        this.rect = rect;
    }



    @Override
    public String toString() {
        return "SolidColor{" +
                "commandName='" + commandName + '\'' +
                ", color=" + color +
                ", rect=" + rect +
                '}';
    }
}
