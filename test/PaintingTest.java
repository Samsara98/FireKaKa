import css.CSSParser;
import css.Stylesheet;
import dom.Node;
import html.HTMLParser;
import layout.LayoutBox;
import painting.Paint;
import style.StyledNode;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PaintingTest {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    public static void main(String[] args) throws IOException {
        String htmlInput = Files.readString(Path.of("res/paint-html-input.html"), StandardCharsets.UTF_8);
        String cssInput = Files.readString(Path.of("res/paint-css-input.css"), StandardCharsets.UTF_8);

        HTMLParser htmlParser = new HTMLParser();
        Node domNode = htmlParser.parse(htmlInput);

        CSSParser cssParser = new CSSParser();
        Stylesheet stylesheet = cssParser.parse(cssInput);

        StyledNode styledRoot = new StyledNode(domNode, stylesheet);
        LayoutBox layoutBoxRoot = new LayoutBox(styledRoot);

        // 根据窗口宽度计算
        layoutBoxRoot.layoutTree(CANVAS_WIDTH);
        Paint paint = new Paint();
        BufferedImage image = paint.paint(layoutBoxRoot, CANVAS_WIDTH, CANVAS_HEIGHT);

        File outputfile = new File("res/test.png");
        ImageIO.write(image, "png", outputfile);

        BufferedImage expectedImage = ImageIO.read(new File("res/paint-test.png"));
        assert identicalImage(image, expectedImage);
    }

    /**
     * 检查两个图片是否相同
     * @param imgA 图片1
     * @param imgB 图片2
     * @return 相同返回true，不同返回false
     */
    public static boolean identicalImage(BufferedImage imgA, BufferedImage imgB) {
        // 检查长宽
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // 逐个像素对比
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}