import css.CSSParser;
import css.Stylesheet;
import dom.Node;
import html.HTMLParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSSTest {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("res/css-input.css"), StandardCharsets.UTF_8);
        String expectedOutput = Files.readString(Path.of("res/css-output.css"), StandardCharsets.UTF_8);

        CSSParser parser = new CSSParser();
        Stylesheet stylesheet = parser.parse(input);
        String output = stylesheet.toString();

        assert output.equals(expectedOutput);
    }
}