package html;

import java.util.function.Predicate;

public class Parser {
    public String input;
    public int pos;

    public Parser() {

    }

    /**
     * 返回当前位置字符，指针后移
     *
     * @return
     */
    public char consumeChar() {
        pos++;
        return input.charAt(pos - 1);
    }

    /**
     * 查看当前位置字符
     *
     * @return
     */
    public char currentChar() {
        return input.charAt(pos);
    }

    /**
     * 查看当前位置之后n个的字符是否为chars
     *
     * @param n
     * @param chars
     * @return
     */
    public boolean startWith(int n, char[] chars) {
        return input.substring(pos, pos + n).equals(new String(chars));
    }

    /**
     * 查看当前读取位置是否到字符错末尾
     *
     * @return
     */
    public boolean finish() {
        return pos >= input.length() - 1;
    }


    /**
     * 读取字符直至...
     *
     * @param predicate 读取结束条件
     * @param c
     * @return
     */
    public String consumeWhile(Predicate<Character> predicate, char c) {

        StringBuilder stringBuffer = new StringBuilder();
        while (!finish() && predicate.test(c)) {
            stringBuffer.append(consumeChar());
        }
        return stringBuffer.toString();
    }

    /**
     * 清除空格
     */
    public void consumeWhiteSpace() {

        if (finish()) {
            return;
        }
        while (currentChar() == ' ' || currentChar() == '\n') {
            consumeChar();
        }
    }
}
