package painting;

import layout.Rect;

import java.awt.*;

public class SolidColor extends DispalyCommand{



    public SolidColor(Color color, Rect rect) {
        commandName = "SolidColor";
        this.color =color;
        this.rect = rect;
    }

}
