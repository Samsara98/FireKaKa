import dom.Node;
import html.HTMLParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTMLTest {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("res/html-input.html"), StandardCharsets.UTF_8);
        String expectedOutput = Files.readString(Path.of("res/html-output.html"), StandardCharsets.UTF_8);

        HTMLParser parser = new HTMLParser();
        Node node = parser.parse(input);
        String output = node.toString();
        System.out.println(output);
        assert output.equals(expectedOutput);
    }
}