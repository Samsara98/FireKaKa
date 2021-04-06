package painting;

import layout.Rect;

import java.awt.*;

public abstract class DispalyCommand {

    String commandName;
    Color color;
    Rect rect;

    @Override
    public String toString() {
        return "DispalyCommand{" +
                "commandName='" + commandName + '\'' +
                ", color=" + color +
                ", rect=" + rect +
                '}';
    }
}
