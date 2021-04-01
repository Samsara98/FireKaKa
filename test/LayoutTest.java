import css.CSSParser;
import css.Stylesheet;
import dom.Node;
import html.HTMLParser;
import layout.LayoutBox;
import style.StyledNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LayoutTest {
    public static void main(String[] args) throws IOException {
        String htmlInput = Files.readString(Path.of("res/layout-html-input.html"), StandardCharsets.UTF_8);
        String cssInput = Files.readString(Path.of("res/layout-css-input.css"), StandardCharsets.UTF_8);
        String expectedOutput = Files.readString(Path.of("res/layout-output.xml"), StandardCharsets.UTF_8);

        HTMLParser htmlParser = new HTMLParser();
        Node domNode = htmlParser.parse(htmlInput);

        CSSParser cssParser = new CSSParser();
        Stylesheet stylesheet = cssParser.parse(cssInput);

        StyledNode styledRoot = new StyledNode(domNode, stylesheet);
        LayoutBox layoutBoxRoot = new LayoutBox(styledRoot);

        /****************************************************************************************
         * layoutTree(int viewportWidth)函数会以viewportWidth为窗口宽度，计算HTML/CSS所生成的布局。
         * 其中，如果用户没有特别指明，根节点margin、border、padding默认全部为0,即根节点的内容区域
         * （content area）对齐窗口左上角。
         ****************************************************************************************/
        layoutBoxRoot.layoutTree(1000);
        String output = layoutBoxRoot.toString();
        System.out.println(output);
        assert output.equals(expectedOutput);
    }
}