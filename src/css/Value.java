package css;


class Value {

    String string;
    int px = -1;
    int color = -1;

    public Value(String value) {

        if(value.startsWith("#")){
            color = Integer.parseInt(value.substring(1),16);
        }else if(value.endsWith("px")){
            px = Integer.parseInt(value.substring(0,value.length()-2));
        }else {
            string = value;
        }

    }

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
