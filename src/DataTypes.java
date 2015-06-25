import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataTypes {

    public static HashMap<String, ArrayList<String>> types = new HashMap<>();

    public static HashMap<String, ArrayList<String>> getDataTypes() {

        //types = new HashMap<>();
        ArrayList<String> integer = new ArrayList<>();
        integer.add("Integer");
        integer.add("int");
        types.put("Integer", integer);
        ArrayList<String> str = new ArrayList<>();
        str.add("String");
        types.put("String", str);
        ArrayList<String> flt = new ArrayList<>();
        flt.add("float");
        flt.add("Float");
        types.put("Float", flt);
        ArrayList<String> dbl = new ArrayList<>();
        dbl.add("Double");
        dbl.add("double");
        types.put("Double", dbl);
        ArrayList<String> bt = new ArrayList<>();
        bt.add("byte");
        bt.add("Byte");
        types.put("Byte", bt);
        ArrayList<String> sht = new ArrayList<>();
        sht.add("short");
        sht.add("Short");
        types.put("Short", sht);
        ArrayList<String> lng = new ArrayList<>();
        lng.add("long");
        lng.add("Long");
        types.put("Long", lng);
        ArrayList<String> bool = new ArrayList<>();
        bool.add("Boolean");
        bool.add("boolean");
        types.put("Boolean", lng);
        ArrayList<String> chr = new ArrayList<>();
        chr.add("char");
        types.put("char", chr);
        ArrayList<String> clss = new ArrayList<>();
        clss.add("public class");
        clss.add("public abstract class");
        clss.add("public final abstract class");
        types.put("Class", clss);
        ArrayList<String> method = new ArrayList<>();
        method.add("public void");
        method.add("public.*\\(.*\\)");
        types.put("Method", method);
        ArrayList<String> ifState = new ArrayList<>();
        method.add("if.*\\(.*");
        types.put("if", ifState);
        ArrayList<String> elseStat = new ArrayList<>();
        method.add("else.*");
        types.put("else", elseStat);
        ArrayList<String> tryStat = new ArrayList<>();
        method.add("try.*");
        types.put("try", tryStat);
        ArrayList<String> catchStat = new ArrayList<>();
        method.add("catch.*");
        types.put("catch", catchStat);

        return types;
    }

}