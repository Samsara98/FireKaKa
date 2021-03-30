package css;


public class Value {

    String string;
    int px = -1;
    int color = -1;

    /**
     * Value可能有三种
     * @param value
     */
    public Value(String value) {

        if(value.startsWith("#")){
            color = Integer.parseInt(value.substring(1),16);
        }else if(value.endsWith("px")){
            px = (int) Double.parseDouble(value.substring(0,value.length()-2));
        }else {
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
        if(px != -1){
            return px +"px";
        }
        if(color != -1){
            return "#"+Integer.toString(color,16);
        }

        return string;
    }
}
