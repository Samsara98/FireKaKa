package css;

import java.util.ArrayList;

public class Stylesheet {


    ArrayList<Rule> rules;

    public Stylesheet(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : rules) {
            ArrayList<String> list = new ArrayList<>();
            for (Selector selector : rule.selectors) {

                StringBuilder stringBuilder1 = new StringBuilder();

                if(!selector.tagName.equals("")){
                    stringBuilder1.append(selector.tagName);
                }

                if (!selector.id.equals("")) {
                    stringBuilder1.append("#").append(selector.id);
                }

                if (selector.className.size() != 0) {
                    for (String className : selector.className) {
                        stringBuilder1.append('.').append(className);
                    }
                }
                list.add(stringBuilder1.toString());
            }
            stringBuilder.append(String.join(", ",list));
            stringBuilder.append(" {\n");

            for (Declaration d:rule.declarations) {
                stringBuilder.append("  ").append(d.key).append(": ").append(d.value.toString()).append(";\n");
            }


            stringBuilder.append("}\n\n");
        }
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }


}
