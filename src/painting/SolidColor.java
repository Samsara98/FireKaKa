package painting;

import layout.Rect;

import java.awt.*;

public class SolidColor extends DispalyCommand{



    public SolidColor(String color, Rect rect) {
        commandName = "SolidColor";
        this.color = new Color(Integer.parseInt(color.substring(1),16));
        this.rect = rect;
    }

}
