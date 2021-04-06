import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import css.CSSParser;
import css.Stylesheet;
import dom.Node;
import html.HTMLParser;
import layout.LayoutBox;
import style.StyledNode;
import painting.Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Browser extends GraphicsProgram {

    private static final int APPLICATION_WIDTH = 800;
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final String URL = "website";

    String html;
    String css;
    public JTextField addressBar;
    public JButton goButton;

    public void init() {
        addressBar = new JTextField(15);
        addressBar.setEditable(true);
        addressBar.setActionCommand("url-text-field");
        addressBar.addActionListener(this);
        add(addressBar, NORTH);

        goButton = new JButton("GO");
        goButton.addActionListener(this);
        add(goButton, NORTH);
    }

    public void run() {
        // 设置窗口和画布大小
        setWidth(APPLICATION_WIDTH);
        setCanvasWidth(CANVAS_WIDTH);
        setCanvasHeight(CANVAS_HEIGHT);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "url-text-field":
            case "GO":
                String url = addressBar.getText().trim();
                try {
                    // 读取HTML和CSS文件
                    html = Files.readString(Path.of("res/" + url + "/index.html"), StandardCharsets.UTF_8);
                    css = Files.readString(Path.of("res/" + url + "/style.css"), StandardCharsets.UTF_8);
                    // 渲染
                    render();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, "文件不存在", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "未知命令", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public void render() {
        clearCanvas();
        HTMLParser htmlParser = new HTMLParser(html);
        Node root = htmlParser.parse();
        CSSParser cssParser = new CSSParser(css);
        Stylesheet stylesheet = cssParser.parse();
        StyledNode styledRoot = new StyledNode(root, stylesheet);
        LayoutBox layoutBox = new LayoutBox(styledRoot);
        layoutBox.layoutTree(getWidth());
        Paint paint = new Paint();
        Image image = paint.paint(layoutBox, CANVAS_WIDTH, CANVAS_HEIGHT);
        GImage gImage = new GImage(image);
        add(gImage);
    }
}


