package css;

class Declaration {

    public String key;
    public Value value;

    public Declaration(String key,String value){
        this.key = key;
        this.value = new Value(value);
    }
}
