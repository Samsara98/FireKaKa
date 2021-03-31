package css;


public class Value {

    String string;
    int px;
    String color;

    /**
     * Value可能有三种
     *
     * @param value
     */
    public Value(String value) {

        if (value.startsWith("#")) {
            int i = Integer.parseInt(value.substring(1), 16);
            if (value.substring(1).length() <= 6 && value.substring(1).length() >= 3 && i >= 0) {
                color = value.substring(1);
            } else {
                color = "";
            }
        } else if (value.endsWith("px")) {
            px = (int) Double.parseDouble(value.substring(0, value.length() - 2));
        } else {
            string = value;
        }

    }

//    public static void main(String[] args) {
//        Value v = new Value("2.1px");
//        System.out.println(v);
//        v = new Value("#e457");
//        System.out.println(v);
//    }

    @Override
    public String toString() {

        if (color != null) {
            if (!color.equals("")) {
                return "#" + color;
            } else {
                return "initial";
            }
        }

        if (string != null) {
            return string;
        }
        return px + "px";
    }
}
