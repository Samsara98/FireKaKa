package layout;

import css.Value;
import style.StyledNode;

public class BoxType {

    public StyledNode styledNode;
    public Type type;

    public BoxType(StyledNode styledNode, Type type) {
        this.styledNode = styledNode;
        assert type!=null;
        this.type = type;
    }

    /**
     * 在CSS中先查询第一个参数所代表的属性，如果没找到就再查询第二个参数代表的属性，如果还没找到，就把第三个参数作为默认值返回
     */
    public Value lookUp(String s1, String s2, String default_){
        if(!styledNode.getAttValue(s1).toString().equals("none")){
            return styledNode.getAttValue(s1);
        }
        if(!styledNode.getAttValue(s2).toString().equals("none")){
            return styledNode.getAttValue(s2);
        }
        return new Value(default_);
    }

    @Override
    public String toString() {
        return "BoxType{" +
                "styledNode=" + styledNode +
                ", type=" + type +
                '}';
    }
}
