package painting;

import layout.Rect;

import java.awt.*;

public interface DispalyCommand {

    public Rect getRect();
    public String getCommandName();
    public Color getColor();

}
